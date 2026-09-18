package com.alfanewgen.galpika.data.repository

import com.alfanewgen.galpika.data.model.Grievance
import com.alfanewgen.galpika.data.model.NewsArticle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions
) {

    private val sampleArticles = listOf(
        NewsArticle(
            id = "sample-1",
            title = "ఎమ్మెల్యేల కోసం కొత్త 'స్పూన్-ఫీడింగ్' యాప్ ప్రారంభించిన సాంకేతిక శాఖ!",
            content = "హైదరాబాద్: శాసనసభ సమావేశాల్లో ఎలాంటి ప్రశ్నలు వేయాలి, ఎప్పుడు లేచి బల్లలు చరచాలి, మైక్ ఇవ్వకపోతే ఎలా అరుచుకోవాలి అనే విషయాలను స్వయంగా నేర్పించేందుకు ఒక వినూత్న మొబైల్ అప్లికేషన్‌ను ప్రభుత్వం ప్రారంభించింది. ఈ యాప్‌లో 'స్పీచ్ రెడీమేడ్ టెంప్లేట్లు', 'విపక్షాలపై కౌంటర్ కొట్టే డైలాగులు' కూడా పొందుపరిచారు. ఈ యాప్ వాడకంలో అత్యుత్తమ ప్రతిభ కనబరిచిన ఎమ్మెల్యేకి ప్రత్యేకంగా 'డిజిటల్ గొంతుక' అవార్డు ఇస్తామని ప్రకటించారు.",
            summary = "అసెంబ్లీలో ప్రశ్నలు వేయడం, నినాదాలు ఇవ్వడం వంటి వాటిని ఆటోమేట్ చేసే వినూత్న యాప్‌పై హాస్య కథనం.",
            category = "రాజకీయ వ్యంగ్యం",
            district = "హైదరాబాద్",
            imageUrl = "https://images.unsplash.com/photo-1541872703-74c5e44368f9?auto=format&fit=crop&w=800&q=80",
            authorName = "శ్రీరామ్ (వ్యంగ్య రిపోర్టర్)",
            createdAt = Date()
        ),
        NewsArticle(
            id = "sample-2",
            title = "సినిమా విడుదల కాగానే రివ్యూవర్లకు ఉచిత బీపీ పరీక్షలు: నిర్మాతల సంఘం కీలక నిర్ణయం!",
            content = "విశాఖపట్నం: ఫస్ట్ డే ఫస్ట్ షో చూసి విశ్లేషణలు రాసే రివ్యూవర్ల మానసిక మరియు శారీరక ఆరోగ్యాన్ని దృష్టిలో ఉంచుకుని థియేటర్ల బయట ఉచిత రక్తపోటు (BP) మరియు గుండె పనితీరు పరీక్ష కేంద్రాలు ఏర్పాటు చేయనున్నట్లు తెలుగు ఫిల్మ్ ఛాంబర్ నిర్ణయించింది. భారీ హైప్ తెచ్చి థియేటర్‌లో నిరాశపరిచే సినిమాల వల్ల రివ్యూవర్లలో ఆవేశం పెరిగిపోతోందని, వారి ప్రాణాల రక్షణకు ఇది అవసరమని ఛాంబర్ ప్రతినిధులు పేర్కొన్నారు.",
            summary = "తెలుగు సినిమా రివ్యూవర్ల ఆరోగ్యంపై నిర్మాతల సంఘం హాస్య స్పందన.",
            category = "సినిమా & వినోదం",
            district = "విశాఖపట్నం",
            imageUrl = "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?auto=format&fit=crop&w=800&q=80",
            authorName = "రవితేజ (సినీ సెటైరిస్ట్)",
            createdAt = Date(System.currentTimeMillis() - 3600000)
        ),
        NewsArticle(
            id = "sample-3",
            title = "రోడ్లపై గుంతలు పూడ్చేందుకు 'చంద్రయాన్ రోవర్' టెక్నాలజీ పరిశీలన!",
            content = "విజయవాడ: నగరంలోని రహదారులపై ఏర్పడిన చంద్రుని ఉపరితలం లాంటి గుంతల్లో ప్రయాణించలేక సతమతమవుతున్న వాహనదారుల కోసం ప్రత్యేక రోవర్ టెక్నాలజీతో కూడిన ఆటోలను ప్రవేశపెట్టాలని స్థానిక మునిసిపాలిటీ యోచిస్తోంది. ఈ ఆటోలు గుంతలో పడినా ఊగకుండా గాల్లోకి ఎగిరి అవతలి ఒడ్డున దిగుతాయని సాంకేతిక నిపుణులు తెలిపారు.",
            summary = "నగర రోడ్ల పరిస్థితిపై సరికొత్త వ్యంగ్యాస్త్రం.",
            category = "సమాజం & వింతలు",
            district = "విజయవాడ",
            imageUrl = "https://images.unsplash.com/photo-1515260268569-9271009adfdb?auto=format&fit=crop&w=800&q=80",
            authorName = "కళ్యాణ్ (స్థానిక పరిశీలకుడు)",
            createdAt = Date(System.currentTimeMillis() - 7200000)
        ),
        NewsArticle(
            id = "sample-4",
            title = "క్రికెట్‌లో డక్ అవుట్ అయిన బ్యాట్స్‌మెన్‌కు ఉచితంగా అర డజను గుడ్లు బహుమతి!",
            content = "బెంగళూరు: ఐపీఎల్ మ్యాచ్‌లలో సున్నా పరుగులకే వెనుదిరిగే ఆటగాళ్ల ప్రోటీన్ అవసరాలను తీర్చేందుకు ఒక ప్రముఖ పౌల్ట్రీ సంస్థ వినూత్న పథకాన్ని ప్రకటించింది. డక్ అవుట్ అయిన ప్రతిసారి వారి ఇంటికి ఫ్రెష్ గుడ్ల ట్రే డెలివరీ చేయబడుతుంది. దీనిపై ఆటగాళ్లు తీవ్ర అసహనం వ్యక్తం చేశారు.",
            summary = "క్రికెట్ ఆటగాళ్ల వైఫల్యాలపై కార్టూన్ హాస్యం.",
            category = "క్రీడా సెటైర్లు",
            district = "ఆంధ్రప్రదేశ్ & తెలంగాణ",
            imageUrl = "https://images.unsplash.com/photo-1540747913346-19e32dc3e97e?auto=format&fit=crop&w=800&q=80",
            authorName = "గల్పిక స్పోర్ట్స్ టీమ్",
            createdAt = Date(System.currentTimeMillis() - 14400000)
        )
    )

    fun getNewsFeed(category: String = "అన్ని", district: String = "అన్ని"): Flow<List<NewsArticle>> = callbackFlow {
        var query: Query = firestore.collection("news")
            .whereEqualTo("status", "published")

        if (category != "అన్ని") {
            query = query.whereEqualTo("category", category)
        }
        if (district != "అన్ని") {
            query = query.whereEqualTo("district", district)
        }

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null || snapshot.isEmpty) {
                // Return samples filtered by category/district if remote is empty
                val filtered = sampleArticles.filter {
                    (category == "అన్ని" || it.category == category) &&
                    (district == "అన్ని" || it.district == district || it.district.contains("ఆంధ్రప్రదేశ్"))
                }
                trySend(filtered)
                return@addSnapshotListener
            }

            val remoteArticles = snapshot.toObjects(NewsArticle::class.java)
            if (remoteArticles.isEmpty()) {
                trySend(sampleArticles)
            } else {
                trySend(remoteArticles)
            }
        }

        awaitClose { listener.remove() }
    }

    suspend fun publishNews(article: NewsArticle): Result<String> {
        return try {
            val data = hashMapOf(
                "title" to article.title,
                "content" to article.content,
                "summary" to article.summary,
                "category" to article.category,
                "district" to article.district,
                "imageUrl" to article.imageUrl,
                "isSatire" to true
            )

            // Try Cloud Function first
            try {
                val callResult = functions.getHttpsCallable("publishNews").call(data).await()
                val resMap = callResult.data as? Map<*, *>
                val newsId = resMap?.get("newsId") as? String ?: "new_id"
                Result.success(newsId)
            } catch (e: Exception) {
                // Fallback to direct Firestore insert
                val docRef = firestore.collection("news").add(article).await()
                Result.success(docRef.id)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun submitGrievance(grievance: Grievance): Result<String> {
        return try {
            val data = hashMapOf(
                "complainantName" to grievance.complainantName,
                "email" to grievance.email,
                "phone" to grievance.phone,
                "articleUrlOrTitle" to grievance.articleUrlOrTitle,
                "complaintReason" to grievance.complaintReason,
                "description" to grievance.description
            )
            try {
                val callResult = functions.getHttpsCallable("submitGrievance").call(data).await()
                val resMap = callResult.data as? Map<*, *>
                val ticketId = resMap?.get("ticketId") as? String ?: "GLP-GR-2026"
                Result.success(ticketId)
            } catch (e: Exception) {
                val ticketId = "GLP-GR-2026-${(10000..99999).random()}"
                firestore.collection("grievances").document(ticketId).set(grievance.copy(ticketId = ticketId)).await()
                Result.success(ticketId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
