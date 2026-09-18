import React from "react";
import { 
  Sparkles, 
  Flame, 
  Quote, 
  TrendingUp, 
  ShieldAlert, 
  Feather 
} from "lucide-react";
import { useNews } from "../context/NewsContext";
import { NewsCard } from "../components/NewsCard";

export const GalpikaView = () => {
  const { newsList, searchQuery, setIsGrievanceOpen } = useNews();

  // Filter news if search query is active
  const filteredNews = newsList.filter(item => {
    if (!searchQuery) return true;
    const q = searchQuery.toLowerCase();
    return item.title.toLowerCase().includes(q) || 
           item.summary.toLowerCase().includes(q) ||
           item.district.toLowerCase().includes(q);
  });

  const featuredNews = filteredNews[0];
  const regularNews = filteredNews.slice(1);

  return (
    <div className="space-y-6 pb-20">
      {/* Editorial Satirical Banner */}
      <div className="bg-gradient-to-br from-brand-900 via-brand-800 to-brand-950 text-white rounded-2xl p-5 sm:p-6 shadow-md relative overflow-hidden">
        {/* Background graphic */}
        <div className="absolute -right-8 -bottom-8 opacity-10 pointer-events-none">
          <Feather size={200} />
        </div>

        <div className="relative z-10 max-w-2xl">
          <div className="inline-flex items-center gap-1.5 bg-brand-700/60 border border-brand-500/30 px-3 py-1 rounded-full text-xs font-ramabhadra text-amber-300 mb-3 shadow-sm">
            <Sparkles size={13} className="text-amber-400" />
            గల్పిక సంపాదకీయ వేదిక (Galpika Satire Hub)
          </div>

          <h2 className="text-2xl sm:text-3xl font-bold font-ramabhadra leading-tight">
            సత్యం చేదుగా ఉంటుంది.. కానీ మా వ్యంగ్యం నవ్వులు పూయిస్తుంది!
          </h2>

          <p className="font-mallanna text-brand-100 text-sm sm:text-base mt-2 leading-relaxed">
            తెలుగు ప్రజల దైనందిన జీవితాలు, రాజకీయ ఎత్తుగడలు, అధికారుల నిక్కచ్చి నిర్ణయాలపై నవ్వుల చురకలు.
          </p>

          {/* Daily Satirical Punch */}
          <div className="mt-4 bg-white/10 backdrop-blur-md rounded-xl p-3 border border-white/10 flex items-start gap-3">
            <Quote size={20} className="text-amber-400 shrink-0 mt-0.5" />
            <div className="font-mallanna text-xs sm:text-sm">
              <strong className="text-amber-300 font-ramabhadra block text-xs">నేటి వ్యంగ్య సూక్తి:</strong>
              "ఎన్నికల ముందు ఇచ్చిన హామీలు నీటి మీద రాతలు కావు... అవి వర్షంలో కరిగిపోయే మన సరికొత్త తారు రోడ్లు!"
            </div>
          </div>
        </div>
      </div>

      {/* Featured Satirical News Highlight */}
      {featuredNews && (
        <section>
          <div className="flex items-center justify-between mb-3 px-1">
            <div className="flex items-center gap-2">
              <Flame size={18} className="text-brand-600" />
              <h3 className="text-lg font-bold font-ramabhadra text-slate-900">
                ప్రధాన వ్యంగ్య కథనం (Top Satire Story)
              </h3>
            </div>
            <span className="text-xs text-brand-600 font-ramabhadra font-semibold">
              ట్రెండింగ్
            </span>
          </div>

          <NewsCard news={featuredNews} featured={true} />
        </section>
      )}

      {/* Grid of Other Galpika Scoops */}
      <section>
        <div className="flex items-center justify-between mb-3 px-1">
          <div className="flex items-center gap-2">
            <TrendingUp size={18} className="text-brand-600" />
            <h3 className="text-lg font-bold font-ramabhadra text-slate-900">
              గల్పిక ప్రత్యేక సంచలనాలు (Latest Scoops)
            </h3>
          </div>
          <span className="text-xs text-slate-500 font-mallanna">
            మొత్తం: {regularNews.length} కథనాలు
          </span>
        </div>

        {regularNews.length > 0 ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            {regularNews.map((news) => (
              <NewsCard key={news.id} news={news} />
            ))}
          </div>
        ) : (
          <div className="text-center py-10 bg-white rounded-2xl border border-slate-200 p-6">
            <p className="font-ramabhadra text-slate-700">మీ శోధనకు తగిన కథనాలు లభించలేదు.</p>
            <p className="font-mallanna text-slate-500 text-sm mt-1">వేరే పదాలతో శోధించండి లేదా ఫిల్టర్‌ని తీసివేయండి.</p>
          </div>
        )}
      </section>

      {/* Bottom IT Rules statutory banner */}
      <div className="bg-brand-50 border border-brand-200 rounded-xl p-4 flex flex-col sm:flex-row items-center justify-between gap-3 text-xs font-mallanna text-brand-900">
        <div className="flex items-center gap-2.5 text-center sm:text-left">
          <ShieldAlert size={20} className="text-brand-700 shrink-0" />
          <p>
            భారతీయ చట్టాలు & ఐటీ నిబంధనలు 2021 ప్రకారం గల్పిక కథనాలన్నీ హాస్య కల్పితాలు మాత్రమే.
          </p>
        </div>
        <button
          onClick={() => setIsGrievanceOpen(true)}
          className="bg-brand-700 hover:bg-brand-800 text-white font-ramabhadra text-xs px-3.5 py-1.5 rounded-lg shrink-0 transition"
        >
          గ్రీవెన్స్ సెల్ సంప్రదించండి
        </button>
      </div>
    </div>
  );
};
