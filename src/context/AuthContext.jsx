import React, { createContext, useContext, useState, useEffect } from "react";
import { 
  signInWithPopup, 
  signOut, 
  onAuthStateChanged 
} from "firebase/auth";
import { doc, getDoc, setDoc } from "firebase/firestore";
import { auth, googleProvider, db, isLiveFirebaseConfigured } from "../firebase";

const AuthContext = createContext(null);

const MOCK_STORAGE_KEY = "galpika_user_session";

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Initialize Auth state
  useEffect(() => {
    if (isLiveFirebaseConfigured && auth) {
      const unsubscribe = onAuthStateChanged(auth, async (firebaseUser) => {
        if (firebaseUser) {
          // Fetch user role from Firestore
          let role = "reader";
          try {
            const userDocRef = doc(db, "users", firebaseUser.uid);
            const userDocSnap = await getDoc(userDocRef);
            if (userDocSnap.exists()) {
              role = userDocSnap.data().role || "reader";
            } else {
              // First time login - save user info
              await setDoc(userDocRef, {
                uid: firebaseUser.uid,
                displayName: firebaseUser.displayName,
                email: firebaseUser.email,
                photoURL: firebaseUser.photoURL,
                role: "reader", // default to reader; admin can promote to reporter
                createdAt: new Date().toISOString()
              });
            }
          } catch (e) {
            console.warn("Firestore role fetch error, using default reader role", e);
          }

          setUser({
            uid: firebaseUser.uid,
            displayName: firebaseUser.displayName || "తెలుగు పాఠకుడు",
            email: firebaseUser.email,
            photoURL: firebaseUser.photoURL || "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=150&q=80",
            role: role
          });
        } else {
          setUser(null);
        }
        setLoading(false);
      });
      return () => unsubscribe();
    } else {
      // Local demo mode: check localStorage for saved session
      const saved = localStorage.getItem(MOCK_STORAGE_KEY);
      if (saved) {
        try {
          setUser(JSON.parse(saved));
        } catch (e) {
          setUser(null);
        }
      }
      setLoading(false);
    }
  }, []);

  // Google Login function
  const loginWithGoogle = async () => {
    setLoading(true);
    if (isLiveFirebaseConfigured && auth && googleProvider) {
      try {
        const result = await signInWithPopup(auth, googleProvider);
        const fbUser = result.user;
        
        let role = "reader";
        try {
          const userDocRef = doc(db, "users", fbUser.uid);
          const userDocSnap = await getDoc(userDocRef);
          if (userDocSnap.exists()) {
            role = userDocSnap.data().role || "reader";
          }
        } catch (err) {
          console.warn("Error fetching role", err);
        }

        const appUser = {
          uid: fbUser.uid,
          displayName: fbUser.displayName || "గూగుల్ యూజర్",
          email: fbUser.email,
          photoURL: fbUser.photoURL,
          role: role
        };
        setUser(appUser);
        setLoading(false);
        return appUser;
      } catch (error) {
        console.error("Google login failed, switching to demo login fallback", error);
      }
    }

    // Interactive Demo Google Login Fallback
    const demoUser = {
      uid: "demo-user-12345",
      displayName: "రామకృష్ణ శర్మ",
      email: "ramakrishna.telugu@gmail.com",
      photoURL: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=150&q=80",
      role: "admin" // Default demo to Admin so user can test all reporter & publishing features
    };
    setUser(demoUser);
    localStorage.setItem(MOCK_STORAGE_KEY, JSON.stringify(demoUser));
    setLoading(false);
    return demoUser;
  };

  // Switch demo roles easily in Profile for testing
  const switchRole = (newRole) => {
    if (!user) return;
    const updated = { ...user, role: newRole };
    setUser(updated);
    localStorage.setItem(MOCK_STORAGE_KEY, JSON.stringify(updated));
  };

  // Sign out function
  const logout = async () => {
    if (isLiveFirebaseConfigured && auth) {
      try {
        await signOut(auth);
      } catch (e) {
        console.error("Firebase sign out error", e);
      }
    }
    setUser(null);
    localStorage.removeItem(MOCK_STORAGE_KEY);
  };

  const isAdmin = user?.role === "admin";
  const isReporter = user?.role === "reporter" || isAdmin;
  const isLoggedIn = Boolean(user);

  return (
    <AuthContext.Provider
      value={{
        user,
        loading,
        isLoggedIn,
        isAdmin,
        isReporter,
        loginWithGoogle,
        logout,
        switchRole,
        isLiveFirebase: isLiveFirebaseConfigured
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
