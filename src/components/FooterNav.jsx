import React from "react";
import { 
  Feather, 
  MapPin, 
  Newspaper, 
  User 
} from "lucide-react";
import { useAuth } from "../context/AuthContext";

export const FooterNav = ({ currentTab, setCurrentTab }) => {
  const { user } = useAuth();

  const navItems = [
    {
      id: "galpika",
      name: "గల్పిక",
      subtext: "Galpika",
      icon: Feather,
    },
    {
      id: "local",
      name: "స్థానిక",
      subtext: "Local",
      icon: MapPin,
    },
    {
      id: "vaarthalu",
      name: "వార్తలు",
      subtext: "Vaarthalu",
      icon: Newspaper,
    },
    {
      id: "profile",
      name: "ప్రొఫైల్",
      subtext: "Profile",
      icon: User,
      hasAvatar: Boolean(user?.photoURL),
      avatarUrl: user?.photoURL
    }
  ];

  return (
    <nav 
      className="fixed bottom-0 left-0 right-0 z-40 bg-white/95 backdrop-blur-md border-t border-slate-200 shadow-[0_-4px_20px_rgba(0,0,0,0.06)]"
      aria-label="Footer Navigation"
    >
      <div className="max-w-md md:max-w-xl mx-auto px-3 py-1 flex items-center justify-around">
        {navItems.map((item) => {
          const isActive = currentTab === item.id;
          const IconComponent = item.icon;

          return (
            <button
              key={item.id}
              onClick={() => setCurrentTab(item.id)}
              className={`flex flex-col items-center justify-center py-1 px-3 rounded-xl transition-all duration-200 relative ${
                isActive 
                  ? "text-brand-700 font-bold" 
                  : "text-slate-500 hover:text-slate-800"
              }`}
            >
              {/* Active Indicator Bar */}
              {isActive && (
                <span className="absolute -top-1 w-8 h-1 bg-brand-600 rounded-full shadow-sm shadow-brand-500/50 transition-all"></span>
              )}

              {/* Icon / Avatar */}
              <div className={`p-1 rounded-lg transition-transform ${isActive ? "scale-110" : "scale-100"}`}>
                {item.hasAvatar && isActive ? (
                  <img 
                    src={item.avatarUrl} 
                    alt="Profile" 
                    className="w-6 h-6 rounded-full border-2 border-brand-600 object-cover" 
                  />
                ) : (
                  <IconComponent 
                    size={22} 
                    className={`${isActive ? "stroke-[2.4] text-brand-600" : "stroke-[1.8]"}`} 
                  />
                )}
              </div>

              {/* Label */}
              <span className={`text-[12px] leading-tight font-ramabhadra tracking-wide mt-0.5 ${isActive ? "text-brand-800 font-bold" : "text-slate-600"}`}>
                {item.name}
              </span>

              {/* Sub-label in English for bilingual clarity */}
              <span className="text-[9px] text-slate-400 font-sans -mt-0.5">
                {item.subtext}
              </span>
            </button>
          );
        })}
      </div>
    </nav>
  );
};
