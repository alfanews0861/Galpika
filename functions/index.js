const functions = require("firebase-functions");
const admin = require("firebase-admin");

if (!admin.apps.length) {
  admin.initializeApp();
}

const db = admin.firestore();

/**
 * 1. వార్తల ప్రచురణ (Publish or submit news)
 * Admin can publish directly; Reporter can submit for review or direct publish if pre-authorized.
 */
exports.publishNews = functions.https.onCall(async (data, context) => {
  // Check auth
  if (!context.auth) {
    throw new functions.https.HttpsError(
      "unauthenticated",
      "వార్తలను ప్రచురించడానికి ముందుగా లాగిన్ అవ్వాలి."
    );
  }

  const uid = context.auth.uid;
  const userDoc = await db.collection("users").doc(uid).get();
  const userData = userDoc.exists ? userDoc.data() : {};
  const userRole = userData.role || (context.auth.token.admin ? "admin" : "reader");

  if (userRole !== "admin" && userRole !== "reporter") {
    throw new functions.https.HttpsError(
      "permission-denied",
      "మీకు వార్తలు సమర్పించే అనుమతి లేదు. దయచేసి అడ్మిన్ అనుమతి పొందండి."
    );
  }

  const { title, content, summary, category, district, imageUrl, isSatire = true } = data;

  if (!title || !content || !category) {
    throw new functions.https.HttpsError(
      "invalid-argument",
      "వార్త శీర్షిక (Title), పూర్తి వివరాలు (Content), మరియు విభాగం (Category) తప్పనిసరి."
    );
  }

  const newsItem = {
    title,
    content,
    summary: summary || content.slice(0, 150) + "...",
    category,
    district: district || "ఆంధ్రప్రదేశ్ & తెలంగాణ",
    imageUrl: imageUrl || "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?auto=format&fit=crop&w=800&q=80",
    isSatire: Boolean(isSatire),
    authorId: uid,
    authorName: userData.displayName || context.auth.token.name || "గల్పిక విలేకరి",
    authorRole: userRole,
    status: userRole === "admin" ? "published" : "published", // Reporters publish instantly or as approved
    likesCount: 0,
    viewsCount: 0,
    createdAt: admin.firestore.FieldValue.serverTimestamp(),
    updatedAt: admin.firestore.FieldValue.serverTimestamp(),
    disclaimer: "ఇది వ్యంగ్య/హాస్య కల్పిత కథనం మాత్రమే. నిజజీవిత వ్యక్తులతో ఎటువంటి సంబంధం లేదు (Satirical Fiction)."
  };

  const docRef = await db.collection("news").add(newsItem);

  return {
    success: true,
    newsId: docRef.id,
    message: "వార్త విజయవంతంగా ప్రచురించబడింది!"
  };
});

/**
 * 2. భారతీయ ఐటీ నిబంధనలు 2021 ప్రకారం గ్రీవెన్స్ (ఫిర్యాదు) నమోదు
 * Information Technology (Intermediary Guidelines and Digital Media Ethics Code) Rules, 2021
 * Grievance acknowledgment within 24 hours & resolution tracking.
 */
exports.submitGrievance = functions.https.onCall(async (data, context) => {
  const { complainantName, email, phone, articleUrlOrTitle, complaintReason, description } = data;

  if (!complainantName || !email || !description) {
    throw new functions.https.HttpsError(
      "invalid-argument",
      "ఫిర్యాదుదారు పేరు, ఇమెయిల్, మరియు సమస్య వివరణ తప్పనిసరి."
    );
  }

  const timestamp = Date.now();
  const grievanceTicketId = `GLP-GR-${new Date().getFullYear()}-${Math.floor(10000 + Math.random() * 90000)}`;

  const grievanceRecord = {
    ticketId: grievanceTicketId,
    complainantName,
    email,
    phone: phone || "",
    articleUrlOrTitle: articleUrlOrTitle || "సాధారణ ఫిర్యాదు",
    complaintReason: complaintReason || "వ్యంగ్య కంటెంట్ అభ్యంతరం",
    description,
    status: "ACKNOWLEDGED",
    submittedAt: admin.firestore.FieldValue.serverTimestamp(),
    ackDeadline: new Date(timestamp + 24 * 60 * 60 * 1000).toISOString(), // 24 hours mandate
    resolutionDeadline: new Date(timestamp + 15 * 24 * 60 * 60 * 1000).toISOString(), // 15 days mandate
    officerNotes: ""
  };

  await db.collection("grievances").doc(grievanceTicketId).set(grievanceRecord);

  return {
    success: true,
    ticketId: grievanceTicketId,
    message: `మీ ఫిర్యాదు నమోదు చేయబడింది. టికెట్ ఐడీ: ${grievanceTicketId}. 24 గంటల్లో మా గ్రీవెన్స్ ఆఫీసర్ దీనిని పరిశీలిస్తారు.`
  };
});

/**
 * 3. యూజర్లకు రిపోర్టర్ లేదా అడ్మిన్ హోదా కేటాయింపు (Role Management)
 */
exports.assignUserRole = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError("unauthenticated", "అడ్మిన్ లాగిన్ అవసరం.");
  }

  const callerUid = context.auth.uid;
  const callerDoc = await db.collection("users").doc(callerUid).get();
  const isCallerAdmin = callerDoc.exists && callerDoc.data().role === "admin";

  if (!isCallerAdmin && !context.auth.token.admin) {
    throw new functions.https.HttpsError("permission-denied", "ఈ చర్య కేవలం ప్రధాన అడ్మిన్‌కు మాత్రమే అనుమతించబడింది.");
  }

  const { targetEmail, targetRole } = data;
  if (!targetEmail || !["admin", "reporter", "reader"].includes(targetRole)) {
    throw new functions.https.HttpsError("invalid-argument", "సరైన ఇమెయిల్ మరియు హోదా (admin/reporter/reader) ఇవ్వండి.");
  }

  const userRecord = await admin.auth().getUserByEmail(targetEmail);
  await db.collection("users").doc(userRecord.uid).set(
    {
      email: targetEmail,
      role: targetRole,
      roleAssignedAt: admin.firestore.FieldValue.serverTimestamp()
    },
    { merge: true }
  );

  return {
    success: true,
    message: `${targetEmail} కి '${targetRole}' హోదా విజయవంతంగా కేటాయించబడింది.`
  };
});

/**
 * 4. న్యూస్ ఫీడ్ HTTP API (Public GET)
 */
exports.getNewsFeed = functions.https.onRequest(async (req, res) => {
  res.set("Access-Control-Allow-Origin", "*");
  try {
    const category = req.query.category;
    const district = req.query.district;
    let query = db.collection("news").where("status", "==", "published").orderBy("createdAt", "desc").limit(30);

    if (category && category !== "అన్ని") {
      query = query.where("category", "==", category);
    }
    if (district && district !== "అన్ని") {
      query = query.where("district", "==", district);
    }

    const snapshot = await query.get();
    const articles = [];
    snapshot.forEach((doc) => {
      articles.push({ id: doc.id, ...doc.data() });
    });

    res.status(200).json({ success: true, count: articles.length, data: articles });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});
