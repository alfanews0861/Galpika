// Firebase Configuration & Initialization
import { initializeApp, getApps } from "firebase/app";
import { getAuth, GoogleAuthProvider } from "firebase/auth";
import { getFirestore } from "firebase/firestore";
import { getFunctions } from "firebase/functions";
import { getStorage } from "firebase/storage";

const firebaseConfig = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY || "AIzaSyDummyKeyForGalpikaNewsPortalDevMode123",
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN || "galpika-news-mock.firebaseapp.com",
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID || "galpika-news-mock",
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET || "galpika-news-mock.appspot.com",
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID || "123456789012",
  appId: import.meta.env.VITE_FIREBASE_APP_ID || "1:123456789012:web:abcdef123456"
};

// Check if valid Firebase credentials are provided in .env
export const isLiveFirebaseConfigured = Boolean(
  import.meta.env.VITE_FIREBASE_API_KEY && 
  import.meta.env.VITE_FIREBASE_PROJECT_ID &&
  !import.meta.env.VITE_FIREBASE_API_KEY.includes("Dummy")
);

let app = null;
let auth = null;
let db = null;
let functions = null;
let storage = null;
let googleProvider = null;

try {
  if (!getApps().length) {
    app = initializeApp(firebaseConfig);
  } else {
    app = getApps()[0];
  }
  auth = getAuth(app);
  db = getFirestore(app);
  functions = getFunctions(app);
  storage = getStorage(app);
  googleProvider = new GoogleAuthProvider();
  googleProvider.setCustomParameters({ prompt: "select_account" });
} catch (error) {
  console.warn("Firebase initialization note: Running in smart local mode.", error);
}

export { app, auth, db, functions, storage, googleProvider };
