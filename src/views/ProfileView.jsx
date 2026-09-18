import React from "react";
import { 
  LogIn, 
  LogOut, 
  ShieldCheck, 
  PenSquare, 
  FileText, 
  ShieldAlert, 
  ExternalLink, 
  UserCheck, 
  Sparkles, 
  Server, 
  ChevronRight,
  User
} from "lucide-react";
import { useAuth } from "../context/AuthContext";
import { useNews } from "../context/NewsContext";
import { LEGAL_POLICIES, GRIEVANCE_OFFICER } from "../data/legalPolicies";

export const ProfileView = () => {
  const { 
    user, 
    isLoggedIn, 
    isAdmin, 
    isReporter, 
    loginWithGoogle, 
    logout, 
    switchRole,
    isLiveFirebase 
  } = useAuth();

  const { 
    setIsPublishOpen, 
    setIsGrievanceOpen, 
    setSelectedPolicy 
  } = useNews();

  return (
    <div className="space-y-6 pb-24">
      {/* 1. Profile / Google Auth Card */}
      <div className="bg-white rounded-2xl border border-slate-200 p-5 sm:p-6 shadow-sm">
        {isLoggedIn ? (
          <div className="space-y-4">
            {/* User Details */}
            <div className="flex flex-col sm:flex-row items-center sm:items-start gap-4 text-center sm:text-left">
              <div className="relative">
                <img
                  src={user.photoURL}
                  alt={user.displayName}
                  className="w-20 h-20 rounded-full border-4 border-brand-200 object-cover shadow-sm"
                />
                <span className="absolute bottom-0 right-0 w-5 h-5 bg-emerald-500 border-2 border-white rounded-full"></span>
              </div>

              <div className="flex-1 space-y-1">
                <div className="flex flex-wrap items-center justify-center sm:justify-start gap-2">
                  <h2 className="text-xl font-bold font-ramabhadra text-slate-900">
                    {user.displayName}
                  </h2>
                  {/* Role Badges */}
                  {user.role === "admin" && (
                    <span className="bg-brand-900 text-amber-300 text-xs font-ramabhadra px-2.5 py-0.5 rounded-full border border-brand-700">
                      👑 ప్రధాన అడ్మిన్ (Admin)
                    </span>
                  )}
                  {user.role === "reporter" && (
                    <span className="bg-brand-100 text-brand-800 text-xs font-ramabhadra px-2.5 py-0.5 rounded-full border border-brand-300">
                      ✍️ అనుమతి పొందిన రిపోర్టర్
                    </span>
                  )}
                  {user.role === "reader" && (
                    <span className="bg-slate-100 text-slate-700 text-xs font-ramabhadra px-2.5 py-0.5 rounded-full">
                      📖 సాధారణ పాఠకుడు
                    </span>
                  )}
                </div>

                <p className="text-xs text-slate-500 font-sans">{user.email}</p>
                <p className="text-xs text-slate-600 font-mallanna">
                  {user.role === "admin" && "మీకు అన్ని వార్తలు ప్రచురించడం, తొలగించడం, రిపోర్టర్లను నిర్వహించే పూర్తి అధికారాలు ఉన్నాయి."}
                  {user.role === "reporter" && "మీరు అధీకృత విలేకరిగా కొత్త వ్యంగ్య కథనాలను సమర్పించవచ్చు."}
                  {user.role === "reader" && "మీరు వార్తలను చదవడం, లైక్ చేయడం, షేర్ చేయడం చేయవచ్చు."}
                </p>
              </div>

              {/* Logout Button */}
              <button
                onClick={logout}
                className="bg-slate-100 hover:bg-rose-50 hover:text-rose-600 text-slate-700 px-3.5 py-2 rounded-xl text-xs font-ramabhadra flex items-center gap-1.5 transition border border-slate-200"
              >
                <LogOut size={16} />
                లాగౌట్
              </button>
            </div>

            {/* Interactive Demo Role Switcher for Testing */}
            <div className="bg-brand-50/60 border border-brand-200 rounded-xl p-3 text-xs font-mallanna space-y-2">
              <div className="flex items-center justify-between">
                <span className="font-ramabhadra text-brand-900 font-bold text-xs">
                  డెమో మోడ్ టెస్టింగ్ (పరీక్షించడానికి రోల్ మార్చుకోండి):
                </span>
                <span className="text-[10px] text-brand-700 font-mono">
                  {isLiveFirebase ? "Firebase Live Mode" : "Local Mock Mode"}
                </span>
              </div>
              <div className="grid grid-cols-3 gap-2">
                <button
                  onClick={() => switchRole("admin")}
                  className={`py-1.5 px-2 rounded-lg text-xs font-ramabhadra transition ${
                    user.role === "admin" 
                      ? "bg-brand-800 text-white shadow" 
                      : "bg-white text-slate-700 border border-slate-200 hover:bg-slate-100"
                  }`}
                >
                  అడ్మిన్ (Admin)
                </button>
                <button
                  onClick={() => switchRole("reporter")}
                  className={`py-1.5 px-2 rounded-lg text-xs font-ramabhadra transition ${
                    user.role === "reporter" 
                      ? "bg-brand-800 text-white shadow" 
                      : "bg-white text-slate-700 border border-slate-200 hover:bg-slate-100"
                  }`}
                >
                  రిపోర్టర్ (Reporter)
                </button>
                <button
                  onClick={() => switchRole("reader")}
                  className={`py-1.5 px-2 rounded-lg text-xs font-ramabhadra transition ${
                    user.role === "reader" 
                      ? "bg-brand-800 text-white shadow" 
                      : "bg-white text-slate-700 border border-slate-200 hover:bg-slate-100"
                  }`}
                >
                  పాఠకుడు (Reader)
                </button>
              </div>
            </div>
          </div>
        ) : (
          /* Not Logged In - Direct Google Login */
          <div className="text-center py-6 space-y-4">
            <div className="w-16 h-16 bg-brand-50 text-brand-600 rounded-2xl flex items-center justify-center mx-auto border border-brand-200 shadow-sm">
              <User size={32} />
            </div>

            <div className="max-w-md mx-auto">
              <h2 className="text-xl font-bold font-ramabhadra text-slate-900">
                గల్పిక ఖాతాలోకి ప్రవేశించండి
              </h2>
              <p className="text-xs sm:text-sm text-slate-600 font-mallanna mt-1">
                వార్తలు రాయడానికి, ఇష్టమైన కథనాలను సేవ్ చేసుకోవడానికి గూగుల్ ద్వారా సులభంగా లాగిన్ అవ్వండి.
              </p>
            </div>

            {/* Google Sign In Button */}
            <div className="pt-2">
              <button
                onClick={loginWithGoogle}
                className="bg-white hover:bg-slate-50 text-slate-800 font-ramabhadra text-sm px-6 py-3 rounded-xl border border-slate-300 shadow-sm hover:shadow transition inline-flex items-center gap-3"
              >
                {/* Google Multicolor SVG Logo */}
                <svg className="w-5 h-5" viewBox="0 0 24 24">
                  <path
                    fill="#4285F4"
                    d="M23.745 12.27c0-.7-.06-1.4-.19-2.07H12v4.51h6.6c-.29 1.52-1.14 2.82-2.4 3.68v3.05h3.88c2.27-2.09 3.66-5.17 3.66-9.17z"
                  />
                  <path
                    fill="#34A853"
                    d="M12 24c3.24 0 5.95-1.08 7.93-2.91l-3.88-3.05c-1.08.72-2.45 1.16-4.05 1.16-3.12 0-5.77-2.1-6.72-4.93H1.25v3.15C3.26 21.36 7.33 24 12 24z"
                  />
                  <path
                    fill="#FBBC05"
                    d="M5.28 14.27c-.25-.72-.38-1.49-.38-2.27s.13-1.55.38-2.27V6.58H1.25C.45 8.16 0 9.94 0 12s.45 3.84 1.25 5.42l4.03-3.15z"
                  />
                  <path
                    fill="#EA4335"
                    d="M12 4.75c1.77 0 3.35.61 4.6 1.8l3.42-3.42C17.95 1.19 15.24 0 12 0 7.33 0 3.26 2.64 1.25 6.58l4.03 3.15c.95-2.83 3.6-4.98 6.72-4.98z"
                  />
                </svg>
                Google తో లాగిన్ అవ్వండి
              </button>
            </div>
          </div>
        )}
      </div>

      {/* 2. Admin & Authorized Reporter News Publishing Studio */}
      <div className="bg-white rounded-2xl border border-slate-200 p-5 shadow-sm space-y-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2">
            <div className="w-8 h-8 rounded-lg bg-brand-100 text-brand-700 flex items-center justify-center">
              <PenSquare size={18} />
            </div>
            <div>
              <h3 className="text-base font-bold font-ramabhadra text-slate-900">
                వార్తల ప్రచురణ విభాగం (Publisher Studio)
              </h3>
              <p className="text-xs text-slate-500 font-mallanna">
                అడ్మిన్ మరియు అనుమతి పొందిన రిపోర్టర్లు వార్తలు రాయడానికి వేదిక.
              </p>
            </div>
          </div>

          {isReporter && (
            <button
              onClick={() => setIsPublishOpen(true)}
              className="bg-brand-600 hover:bg-brand-700 text-white font-ramabhadra text-xs px-4 py-2 rounded-xl flex items-center gap-1.5 shadow-sm transition"
            >
              <PenSquare size={14} />
              కొత్త వార్త రాయండి
            </button>
          )}
        </div>

        {!isReporter && (
          <div className="bg-slate-50 border border-slate-200 rounded-xl p-3.5 text-xs font-mallanna text-slate-600 flex items-start gap-2">
            <UserCheck size={18} className="text-brand-600 shrink-0 mt-0.5" />
            <div>
              <p className="font-semibold text-slate-800">విలేకరి అనుమతి (Reporter Privileges):</p>
              <p className="mt-0.5">
                వార్తలు రాయడానికి మీకు అడ్మిన్ ద్వారా రిపోర్టర్ అనుమతి ఉండాలి. పైన డెమో బటన్ల ద్వారా 'అడ్మిన్' లేదా 'రిపోర్టర్' రోల్ ఎంచుకుని వార్త రాయడాన్ని ఇప్పుడే పరీక్షించండి!
              </p>
            </div>
          </div>
        )}
      </div>

      {/* 3. Indian Legal Policies & Compliances (భారతీయ చట్టాల ప్రకారం చట్టబద్ధమైన పేజీలు) */}
      <div className="bg-white rounded-2xl border border-slate-200 p-5 shadow-sm space-y-4">
        <div>
          <div className="flex items-center gap-2">
            <ShieldCheck size={20} className="text-brand-700" />
            <h3 className="text-base font-bold font-ramabhadra text-slate-900">
              చట్టబద్ధమైన విధానాలు & నిబంధనలు (Legal & Policies)
            </h3>
          </div>
          <p className="text-xs text-slate-500 font-mallanna mt-1">
            గల్పిక ప్రధానంగా వ్యంగ్య వార్తా పోర్టల్ కావున, భారతీయ చట్టాలు & ఐటీ నిబంధనలు 2021 ప్రకారం రూపొందించిన విధాన పత్రాలు:
          </p>
        </div>

        {/* Policy Links List */}
        <div className="divide-y divide-slate-100 border border-slate-100 rounded-xl overflow-hidden">
          {LEGAL_POLICIES.map((policy) => (
            <button
              key={policy.id}
              onClick={() => setSelectedPolicy(policy)}
              className="w-full text-left p-3.5 hover:bg-slate-50 transition flex items-center justify-between group font-mallanna"
            >
              <div className="space-y-0.5">
                <h4 className="text-sm font-bold font-ramabhadra text-slate-800 group-hover:text-brand-700 transition">
                  {policy.title}
                </h4>
                <p className="text-xs text-slate-500 line-clamp-1">
                  {policy.shortDesc}
                </p>
              </div>
              <ChevronRight size={18} className="text-slate-400 group-hover:text-brand-600 transition shrink-0 ml-2" />
            </button>
          ))}
        </div>

        {/* Grievance Cell Quick Action Box */}
        <div className="bg-amber-50/80 border border-amber-200 rounded-xl p-4 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-3 text-xs font-mallanna">
          <div>
            <span className="font-bold font-ramabhadra text-amber-950 flex items-center gap-1">
              <ShieldAlert size={14} className="text-amber-700" />
              ఫిర్యాదుల పరిష్కార అధికారి (Grievance Officer):
            </span>
            <p className="text-amber-900/90 mt-0.5">
              {GRIEVANCE_OFFICER.name} • ఇమెయిల్: {GRIEVANCE_OFFICER.email}
            </p>
          </div>
          <button
            onClick={() => setIsGrievanceOpen(true)}
            className="bg-brand-900 hover:bg-brand-950 text-white font-ramabhadra text-xs px-3.5 py-2 rounded-lg shrink-0 shadow-sm transition"
          >
            ఫిర్యాదు చేయండి (File Complaint)
          </button>
        </div>
      </div>

      {/* 4. GCP & Firebase Architecture Status */}
      <div className="bg-slate-100 rounded-2xl p-4 text-xs font-mallanna text-slate-600 space-y-2 border border-slate-200">
        <div className="flex items-center gap-2 font-ramabhadra text-slate-800 font-bold">
          <Server size={14} className="text-brand-600" />
          క్లౌడ్ ఆర్కిటెక్చర్ వివరాలు (GCP / Firebase Functions)
        </div>
        <p>
          ఈ అప్లికేషన్ <strong>Google Cloud Platform (GCP)</strong> మరియు <strong>Firebase</strong> (Cloud Firestore, Authentication, Cloud Functions, Hosting) బ్యాకెండ్ ఆర్కిటెక్చర్‌పై నిర్మించబడింది.
        </p>
        <div className="flex flex-wrap gap-2 pt-1 font-mono text-[10px]">
          <span className="bg-white px-2 py-0.5 rounded border border-slate-300">Functions: Node.js 18</span>
          <span className="bg-white px-2 py-0.5 rounded border border-slate-300">DB: Cloud Firestore</span>
          <span className="bg-white px-2 py-0.5 rounded border border-slate-300">Auth: Google Sign-In</span>
          <span className="bg-white px-2 py-0.5 rounded border border-slate-300">Typography: Ramabhadra & Mallanna</span>
        </div>
      </div>
    </div>
  );
};
