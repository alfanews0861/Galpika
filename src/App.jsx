import React, { useState } from "react";
import { AuthProvider } from "./context/AuthContext";
import { NewsProvider } from "./context/NewsContext";
import { Header } from "./components/Header";
import { FooterNav } from "./components/FooterNav";
import { GalpikaView } from "./views/GalpikaView";
import { LocalView } from "./views/LocalView";
import { VaarthaluView } from "./views/VaarthaluView";
import { ProfileView } from "./views/ProfileView";
import { NewsDetailModal } from "./components/NewsDetailModal";
import { PublisherStudio } from "./views/PublisherStudio";
import { GrievanceModal } from "./components/GrievanceModal";
import { PolicyViewer } from "./components/PolicyViewer";

function AppContent() {
  // Current bottom tab: 'galpika' | 'local' | 'vaarthalu' | 'profile'
  const [currentTab, setCurrentTab] = useState("galpika");

  return (
    <div className="min-h-screen bg-slate-50 flex flex-col font-body">
      {/* Top Application Header */}
      <Header />

      {/* Main Content Area */}
      <main className="flex-1 max-w-5xl w-full mx-auto px-4 py-4 sm:py-6">
        {currentTab === "galpika" && <GalpikaView />}
        {currentTab === "local" && <LocalView />}
        {currentTab === "vaarthalu" && <VaarthaluView />}
        {currentTab === "profile" && <ProfileView />}
      </main>

      {/* Bottom Footer Navigation (4 Icons: Galpika, Local, Vaarthalu, Profile) */}
      <FooterNav currentTab={currentTab} setCurrentTab={setCurrentTab} />

      {/* Global Modals */}
      <NewsDetailModal />
      <PublisherStudio />
      <GrievanceModal />
      <PolicyViewer />
    </div>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <NewsProvider>
        <AppContent />
      </NewsProvider>
    </AuthProvider>
  );
}
