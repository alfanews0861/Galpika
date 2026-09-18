import React from "react";
import { X, Shield, FileCheck, ShieldAlert } from "lucide-react";
import { useNews } from "../context/NewsContext";

export const PolicyViewer = () => {
  const { selectedPolicy, setSelectedPolicy, setIsGrievanceOpen } = useNews();

  if (!selectedPolicy) return null;

  return (
    <div className="fixed inset-0 z-50 bg-slate-900/60 backdrop-blur-sm flex items-center justify-center p-3 sm:p-4 overflow-y-auto">
      <div 
        className="bg-white w-full max-w-2xl rounded-2xl shadow-2xl overflow-hidden flex flex-col max-h-[88vh] animate-in fade-in zoom-in-95 duration-200"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Policy Header */}
        <div className="bg-brand-900 text-white px-5 py-4 flex items-center justify-between">
          <div className="flex items-center gap-2.5">
            <div className="w-8 h-8 rounded-lg bg-brand-700/80 flex items-center justify-center text-brand-200">
              <Shield size={18} />
            </div>
            <div>
              <h2 className="font-ramabhadra text-lg leading-tight">
                {selectedPolicy.title}
              </h2>
              <span className="text-[11px] text-brand-300 font-mallanna">
                భారతీయ చట్టాల ప్రకారం అధికారిక పత్రం • అమలు తేదీ: {selectedPolicy.effectiveDate}
              </span>
            </div>
          </div>
          <button
            onClick={() => setSelectedPolicy(null)}
            className="p-1.5 rounded-full text-brand-200 hover:text-white hover:bg-brand-800 transition"
            aria-label="మూసివేయి"
          >
            <X size={20} />
          </button>
        </div>

        {/* Policy Body */}
        <div className="p-5 sm:p-6 overflow-y-auto space-y-4 font-mallanna text-sm text-slate-700 leading-relaxed">
          <div className="bg-brand-50 border border-brand-200 rounded-xl p-3 text-xs text-brand-800 font-mallanna">
            {selectedPolicy.shortDesc}
          </div>

          <div className="whitespace-pre-line prose prose-slate max-w-none prose-headings:font-ramabhadra prose-headings:text-slate-900">
            {selectedPolicy.content}
          </div>
        </div>

        {/* Policy Footer */}
        <div className="bg-slate-50 border-t border-slate-200 px-5 py-3 flex items-center justify-between">
          <button
            onClick={() => {
              setSelectedPolicy(null);
              setIsGrievanceOpen(true);
            }}
            className="text-xs text-amber-700 hover:text-amber-800 font-ramabhadra flex items-center gap-1.5"
          >
            <ShieldAlert size={14} />
            ఫిర్యాదు దాఖలు చేయండి
          </button>

          <button
            onClick={() => setSelectedPolicy(null)}
            className="bg-brand-600 hover:bg-brand-700 text-white font-ramabhadra text-xs px-4 py-2 rounded-lg transition"
          >
            అర్థమైంది (Close)
          </button>
        </div>
      </div>
    </div>
  );
};
