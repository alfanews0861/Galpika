import React from "react";
import { MapPin, Navigation, Compass, AlertCircle } from "lucide-react";
import { useNews } from "../context/NewsContext";
import { NewsCard } from "../components/NewsCard";
import { TELUGU_STATES } from "../data/teluguDistricts";

export const LocalView = () => {
  const { newsList, activeDistrict, setActiveDistrict, searchQuery } = useNews();

  // Filter by selected district & search query
  const filteredLocalNews = newsList.filter((item) => {
    // District match
    const districtMatch = 
      activeDistrict === "all" || 
      item.district?.toLowerCase().includes(activeDistrict.toLowerCase()) ||
      (activeDistrict === "hyderabad" && item.district?.includes("హైదరాబాద్")) ||
      (activeDistrict === "amaravati" && (item.district?.includes("అమరావతి") || item.district?.includes("విజయవాడ"))) ||
      (activeDistrict === "visakhapatnam" && item.district?.includes("విశాఖపట్నం")) ||
      (activeDistrict === "warangal" && item.district?.includes("వరంగల్"));

    if (!districtMatch) return false;

    // Search query match
    if (searchQuery) {
      const q = searchQuery.toLowerCase();
      return (
        item.title.toLowerCase().includes(q) ||
        item.summary.toLowerCase().includes(q) ||
        item.district.toLowerCase().includes(q)
      );
    }

    return true;
  });

  return (
    <div className="space-y-5 pb-20">
      {/* Local Banner */}
      <div className="bg-brand-800 text-white rounded-2xl p-5 shadow-sm">
        <div className="flex items-center gap-2 text-amber-300 text-xs font-ramabhadra mb-1">
          <MapPin size={14} />
          <span>స్థానిక వ్యంగ్య వార్తలు (Local Satire)</span>
        </div>
        <h2 className="text-xl sm:text-2xl font-bold font-ramabhadra">
          మన ఊరు.. మన గల్లీ.. మనోళ్ళ సరదా గోల!
        </h2>
        <p className="text-xs sm:text-sm text-brand-100 font-mallanna mt-1">
          మున్సిపల్ పనులు, రోడ్ల గుంతలు, స్థానిక రాజకీయ నాయకుల సంచలనాలు.
        </p>
      </div>

      {/* Horizontal District Selector Chips */}
      <div>
        <div className="flex items-center justify-between mb-2 px-1">
          <span className="text-xs font-bold font-ramabhadra text-slate-700 flex items-center gap-1">
            <Compass size={13} className="text-brand-600" />
            జిల్లా లేదా నగరాన్ని ఎంచుకోండి:
          </span>
          {activeDistrict !== "all" && (
            <button
              onClick={() => setActiveDistrict("all")}
              className="text-[11px] text-brand-600 hover:underline font-mallanna"
            >
              అన్ని ప్రాంతాలు
            </button>
          )}
        </div>

        <div className="flex items-center gap-2 overflow-x-auto pb-2 no-scrollbar scroll-smooth">
          {TELUGU_STATES.map((dist) => {
            const isSelected = activeDistrict === dist.id;
            return (
              <button
                key={dist.id}
                onClick={() => setActiveDistrict(dist.id)}
                className={`px-3.5 py-1.5 rounded-xl text-xs whitespace-nowrap transition-all duration-200 shrink-0 font-ramabhadra flex items-center gap-1.5 ${
                  isSelected
                    ? "bg-brand-600 text-white shadow-md shadow-brand-500/20"
                    : "bg-white text-slate-700 border border-slate-200 hover:border-brand-300 hover:bg-brand-50/50"
                }`}
              >
                <MapPin size={12} className={isSelected ? "text-white" : "text-brand-500"} />
                {dist.name}
              </button>
            );
          })}
        </div>
      </div>

      {/* News Grid for Selected District */}
      <section>
        <div className="flex items-center justify-between mb-3 px-1">
          <h3 className="text-base font-bold font-ramabhadra text-slate-900">
            {activeDistrict === "all" ? "అన్ని ప్రాంతాల స్థానిక వార్తలు" : `ఎంచుకున్న ప్రాంత వార్తలు`}
          </h3>
          <span className="text-xs text-slate-500 font-mallanna">
            {filteredLocalNews.length} వార్తలు
          </span>
        </div>

        {filteredLocalNews.length > 0 ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            {filteredLocalNews.map((news) => (
              <NewsCard key={news.id} news={news} />
            ))}
          </div>
        ) : (
          <div className="text-center py-12 bg-white rounded-2xl border border-slate-200 p-6 space-y-3">
            <div className="w-12 h-12 bg-brand-50 text-brand-600 rounded-full flex items-center justify-center mx-auto">
              <AlertCircle size={24} />
            </div>
            <div>
              <p className="font-ramabhadra text-slate-800 text-base">
                ఈ ప్రాంతానికి సంబంధించి ఇంకా కథనాలు లేవు.
              </p>
              <p className="font-mallanna text-slate-500 text-sm mt-1">
                మీరు అనుమతి పొందిన రిపోర్టర్ అయితే ప్రొఫైల్‌లోకి వెళ్లి వెంటనే వార్తను జోడించండి!
              </p>
            </div>
            <button
              onClick={() => setActiveDistrict("all")}
              className="bg-brand-600 text-white px-4 py-1.5 rounded-lg text-xs font-ramabhadra"
            >
              అన్ని ప్రాంతాల వార్తలు చూడండి
            </button>
          </div>
        )}
      </section>
    </div>
  );
};
