import React, { useState } from "react";
import { 
  Search, 
  X, 
  Sparkles, 
  Feather, 
  ShieldAlert, 
  Plus, 
  Minus,
  Radio
} from "lucide-react";
import { useNews } from "../context/NewsContext";

export const Header = () => {
  const { 
    searchQuery, 
    setSearchQuery, 
    fontSizeStep, 
    setFontSizeStep, 
    setIsGrievanceOpen 
  } = useNews();
  
  const [showSearch, setShowSearch] = useState(false);

  // Telugu current date
  const today = new Date();
  const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
  const teluguDate = today.toLocaleDateString('te-IN', options);

  const satiricalTickers = [
    "ఫ్లాష్: కాంట్రాక్టర్ల అసోసియేషన్ డిమాండ్ - 'వర్షాలు రోడ్లను తాకకుండా క్లౌడ్ డైవర్షన్ టెక్నాలజీ తీసుకురావాలి!'",
    "బ్రేకింగ్: వీఐపీలకు రోజుకు 28 గంటలు కేటాయించేలా గడియారాల తయారీ సంస్థలకు విజ్ఞప్తి!",
    "తాజా వ్యంగ్యం: నెలకు 1000 రీల్స్ చూసిన నెటిజన్లకు 'డిజిటల్ పద్మశ్రీ' ఇవ్వాలని సోషల్ మీడియా యూనియన్ వినతి!"
  ];

  return (
    <header className="sticky top-0 z-30 bg-white border-b border-brand-200 shadow-sm">
      {/* Top micro-bar: Indian Legal Satire Warning & Date */}
      <div className="bg-brand-900 text-brand-100 text-xs px-3 py-1.5 flex justify-between items-center tracking-wide">
        <div className="flex items-center gap-1.5 overflow-hidden">
          <span className="bg-brand-700 text-white font-semibold px-1.5 py-0.5 rounded text-[10px] uppercase tracking-wider shrink-0">
            వ్యంగ్య వేదిక
          </span>
          <p className="truncate text-brand-200 text-xs">
            గల్పిక కథనాలు సమకాలీన అంశాలపై వ్యంగ్య/హాస్య కల్పితాలు మాత్రమే.
          </p>
        </div>
        <div className="hidden sm:flex items-center gap-3 shrink-0 text-brand-300">
          <span>{teluguDate}</span>
          <button 
            onClick={() => setIsGrievanceOpen(true)}
            className="text-amber-300 hover:text-amber-200 underline text-xs flex items-center gap-1"
            title="ఐటీ నిబంధనలు 2021 ప్రకారం ఫిర్యాదు చేయండి"
          >
            <ShieldAlert size={12} />
            ఫిర్యాదుల వేదిక
          </button>
        </div>
      </div>

      {/* Main Branding Header */}
      <div className="max-w-5xl mx-auto px-4 py-3 flex items-center justify-between gap-4">
        {/* Logo & Title */}
        <div className="flex items-center gap-2.5">
          <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-brand-800 to-brand-600 flex items-center justify-center text-white shadow-md shadow-brand-500/20">
            <Feather size={22} className="stroke-[2.2]" />
          </div>
          <div>
            <div className="flex items-center gap-1.5">
              <h1 className="text-2xl md:text-3xl font-bold text-brand-900 font-ramabhadra tracking-tight leading-none">
                గల్పిక
              </h1>
              <span className="text-[11px] bg-brand-100 text-brand-700 font-semibold px-1.5 py-0.5 rounded-full border border-brand-200">
                సెటైర్ న్యూస్
              </span>
            </div>
            <p className="text-xs text-brand-700 font-mallanna -mt-0.5">
              సమకాలీన సత్యాలపై సునిశిత వ్యంగ్యాస్త్రాలు
            </p>
          </div>
        </div>

        {/* Right Tools: Font Scaler & Search Toggle */}
        <div className="flex items-center gap-1.5 sm:gap-2">
          {/* Font Scaler (A- / A+) for Telugu reader accessibility */}
          <div className="flex items-center bg-slate-100 rounded-lg p-0.5 border border-slate-200 text-slate-700 text-xs">
            <button 
              onClick={() => setFontSizeStep(prev => Math.max(-1, prev - 1))}
              className={`p-1.5 rounded hover:bg-white transition ${fontSizeStep === -1 ? 'text-brand-600 font-bold bg-white' : ''}`}
              title="చిన్న అక్షరాలు"
            >
              <Minus size={13} />
            </button>
            <span className="px-1 text-[11px] font-medium select-none">అ</span>
            <button 
              onClick={() => setFontSizeStep(prev => Math.min(2, prev + 1))}
              className={`p-1.5 rounded hover:bg-white transition ${fontSizeStep > 0 ? 'text-brand-600 font-bold bg-white' : ''}`}
              title="పెద్ద అక్షరాలు"
            >
              <Plus size={13} />
            </button>
          </div>

          {/* Search Toggle */}
          <button
            onClick={() => setShowSearch(!showSearch)}
            className={`p-2 rounded-lg border transition ${
              showSearch 
                ? 'bg-brand-600 text-white border-brand-600 shadow-sm' 
                : 'bg-slate-50 text-slate-700 border-slate-200 hover:bg-slate-100'
            }`}
            aria-label="శోధన"
          >
            {showSearch ? <X size={18} /> : <Search size={18} />}
          </button>
        </div>
      </div>

      {/* Expandable Search Input */}
      {showSearch && (
        <div className="bg-brand-50 border-t border-brand-200 px-4 py-2.5 transition-all">
          <div className="max-w-5xl mx-auto relative">
            <input
              type="text"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              placeholder="వార్తలలో శోధించండి... (ఉదా: ఆధార్, బస్సు, రోడ్డు, ఎంపీ)..."
              className="w-full bg-white border border-brand-300 rounded-lg pl-10 pr-10 py-2 text-sm text-slate-800 placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-brand-500 font-mallanna text-base"
              autoFocus
            />
            <Search size={18} className="absolute left-3 top-2.5 text-brand-600" />
            {searchQuery && (
              <button 
                onClick={() => setSearchQuery("")}
                className="absolute right-3 top-2.5 text-slate-400 hover:text-slate-600"
              >
                <X size={16} />
              </button>
            )}
          </div>
        </div>
      )}

      {/* Live Satirical Ticker Bar */}
      <div className="bg-gradient-to-r from-brand-700 via-brand-600 to-brand-800 text-white py-1 px-3 text-xs flex items-center gap-2 overflow-hidden shadow-inner">
        <span className="flex items-center gap-1 bg-amber-400 text-brand-950 font-bold px-2 py-0.5 rounded text-[10px] shrink-0 font-ramabhadra tracking-wide uppercase">
          <Radio size={12} className="animate-pulse" />
          గల్పిక సెటైర్ బులిటెన్
        </span>
        <div className="marquee-container overflow-hidden whitespace-nowrap w-full">
          <p className="inline-block animate-marquee font-mallanna text-sm text-brand-50">
            {satiricalTickers.join("   ★   ")}
          </p>
        </div>
      </div>
    </header>
  );
};
