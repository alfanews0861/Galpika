import React from "react";
import { 
  Flame, 
  Landmark, 
  Clapperboard, 
  MapPin, 
  Laptop, 
  Trophy, 
  Smile, 
  Sparkles,
  Layers
} from "lucide-react";
import { useNews } from "../context/NewsContext";
import { NewsCard } from "../components/NewsCard";
import { NEWS_CATEGORIES } from "../data/teluguDistricts";

export const VaarthaluView = () => {
  const { newsList, activeCategory, setActiveCategory, searchQuery } = useNews();

  const getCategoryIcon = (iconName) => {
    switch (iconName) {
      case "Landmark": return <Landmark size={14} />;
      case "Clapperboard": return <Clapperboard size={14} />;
      case "MapPin": return <MapPin size={14} />;
      case "Laptop": return <Laptop size={14} />;
      case "Trophy": return <Trophy size={14} />;
      case "Smile": return <Smile size={14} />;
      case "Sparkles": return <Sparkles size={14} />;
      default: return <Flame size={14} />;
    }
  };

  // Filter news
  const filteredNews = newsList.filter((item) => {
    // Category match
    const categoryMatch = activeCategory === "all" || item.category === activeCategory;
    if (!categoryMatch) return false;

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
      {/* Categories Horizontal Bar */}
      <div className="sticky top-[102px] z-20 bg-slate-50/95 backdrop-blur-md py-2 -mx-4 px-4 border-b border-slate-200/80">
        <div className="flex items-center gap-1.5 overflow-x-auto no-scrollbar scroll-smooth">
          {NEWS_CATEGORIES.map((cat) => {
            const isSelected = activeCategory === cat.id;
            return (
              <button
                key={cat.id}
                onClick={() => setActiveCategory(cat.id)}
                className={`px-3.5 py-1.5 rounded-full text-xs whitespace-nowrap transition-all duration-200 shrink-0 font-ramabhadra flex items-center gap-1.5 ${
                  isSelected
                    ? "bg-brand-700 text-white shadow-sm"
                    : "bg-white text-slate-700 border border-slate-200 hover:border-brand-300 hover:bg-slate-50"
                }`}
              >
                {getCategoryIcon(cat.icon)}
                <span>{cat.name}</span>
              </button>
            );
          })}
        </div>
      </div>

      {/* Feed Title / Status */}
      <div className="flex items-center justify-between px-1">
        <div className="flex items-center gap-2">
          <Layers size={18} className="text-brand-600" />
          <h2 className="text-lg font-bold font-ramabhadra text-slate-900">
            {NEWS_CATEGORIES.find((c) => c.id === activeCategory)?.name || "వార్తలు"}
          </h2>
          {searchQuery && (
            <span className="text-xs bg-brand-100 text-brand-800 px-2 py-0.5 rounded-full font-mallanna">
              "{searchQuery}" కోసం ఫలితాలు
            </span>
          )}
        </div>
        <span className="text-xs text-slate-500 font-mallanna">
          మొత్తం: {filteredNews.length}
        </span>
      </div>

      {/* News Grid */}
      {filteredNews.length > 0 ? (
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          {filteredNews.map((news) => (
            <NewsCard key={news.id} news={news} />
          ))}
        </div>
      ) : (
        <div className="text-center py-16 bg-white rounded-2xl border border-slate-200 p-6">
          <p className="font-ramabhadra text-slate-800 text-base">ఈ విభాగంలో కథనాలు లేవు.</p>
          <p className="font-mallanna text-slate-500 text-sm mt-1">వేరే విభాగాన్ని ఎంచుకోండి లేదా శోధనను క్లియర్ చేయండి.</p>
        </div>
      )}
    </div>
  );
};
