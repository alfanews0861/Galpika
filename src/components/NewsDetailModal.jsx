import React from "react";
import { 
  X, 
  Heart, 
  Share2, 
  Bookmark, 
  MapPin, 
  Clock, 
  Sparkles, 
  AlertTriangle, 
  ShieldAlert, 
  Check, 
  Copy 
} from "lucide-react";
import { useNews } from "../context/NewsContext";

export const NewsDetailModal = () => {
  const { 
    selectedNews, 
    setSelectedNews, 
    likedIds, 
    toggleLike, 
    bookmarkedIds, 
    toggleBookmark,
    getFontSizeClass,
    setIsGrievanceOpen
  } = useNews();

  const [copied, setCopied] = React.useState(false);

  if (!selectedNews) return null;

  const isLiked = likedIds.includes(selectedNews.id);
  const isBookmarked = bookmarkedIds.includes(selectedNews.id);

  const handleCopyLink = () => {
    navigator.clipboard.writeText(window.location.href);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  const handleWhatsAppShare = () => {
    const text = `*${selectedNews.title}*\n\nగల్పిక తెలుగు వ్యంగ్య వేదిక కథనం:\n${selectedNews.summary}\n\nపూర్తిగా చదవండి: ${window.location.origin}`;
    window.open(`https://api.whatsapp.com/send?text=${encodeURIComponent(text)}`, "_blank");
  };

  return (
    <div className="fixed inset-0 z-50 bg-slate-900/60 backdrop-blur-sm flex items-end sm:items-center justify-center p-0 sm:p-4 overflow-y-auto">
      <div 
        className="bg-white w-full sm:max-w-2xl max-h-[92vh] rounded-t-3xl sm:rounded-2xl shadow-2xl flex flex-col overflow-hidden animate-in fade-in slide-in-from-bottom-6 duration-200"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Modal Sticky Header */}
        <div className="sticky top-0 bg-white/95 backdrop-blur-md border-b border-slate-200 px-4 py-3 flex items-center justify-between z-10">
          <div className="flex items-center gap-2">
            <span className="bg-brand-900 text-amber-300 text-xs font-ramabhadra px-2.5 py-0.5 rounded-full flex items-center gap-1">
              <Sparkles size={12} className="text-amber-400" />
              గల్పిక వ్యంగ్య కథనం
            </span>
            {selectedNews.district && (
              <span className="text-xs text-slate-500 font-mallanna flex items-center gap-0.5">
                <MapPin size={12} className="text-brand-600" />
                {selectedNews.district}
              </span>
            )}
          </div>

          <button
            onClick={() => setSelectedNews(null)}
            className="p-1.5 rounded-full bg-slate-100 hover:bg-slate-200 text-slate-600 transition"
            aria-label="మూసివేయి"
          >
            <X size={20} />
          </button>
        </div>

        {/* Scrollable Content */}
        <div className="overflow-y-auto px-4 sm:px-6 py-4 space-y-4">
          {/* Cover Image */}
          <div className="relative aspect-video w-full rounded-xl overflow-hidden bg-slate-100 shadow-inner">
            <img
              src={selectedNews.imageUrl}
              alt={selectedNews.title}
              className="w-full h-full object-cover"
              onError={(e) => {
                e.target.src = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?auto=format&fit=crop&w=800&q=80";
              }}
            />
          </div>

          {/* Satirical Legal Disclaimer Banner (భారతీయ చట్టాల ప్రకారం పేరడీ హెచ్చరిక) */}
          <div className="bg-amber-50 border border-amber-200 rounded-xl p-3 text-amber-900 text-xs flex gap-2.5 items-start font-mallanna">
            <AlertTriangle size={18} className="text-amber-600 shrink-0 mt-0.5" />
            <div>
              <strong className="font-ramabhadra text-amber-950 block text-sm">
                చట్టబద్ధమైన పేరడీ & వ్యంగ్య నిరాకరణ:
              </strong>
              <p className="mt-0.5 leading-relaxed text-amber-900/90">
                ఈ కథనం పూర్తిగా వినోదం, హాస్యం మరియు సామాజిక సమీక్ష కోసం ఉద్దేశించిన కల్పిత వ్యంగ్య రూపకం. నిజజీవిత వ్యక్తుల వ్యక్తిగత గౌరవానికి భంగం కలిగించే ఉద్దేశం లేదు. భారత రాజ్యాంగంలోని అధికరణ 19(1)(a) మరియు ఐటీ చట్టం 2000 సెక్షన్ 79 పరిధిలో ప్రచురితం.
              </p>
            </div>
          </div>

          {/* Title in Ramabhadra */}
          <h1 className="text-xl sm:text-2xl md:text-3xl font-bold font-ramabhadra text-slate-900 leading-snug">
            {selectedNews.title}
          </h1>

          {/* Author & Timestamp */}
          <div className="flex items-center justify-between text-xs text-slate-500 py-2 border-y border-slate-100 font-mallanna">
            <div className="flex items-center gap-2">
              <span className="font-semibold text-brand-700">{selectedNews.authorName}</span>
              <span>•</span>
              <span className="flex items-center gap-1">
                <Clock size={12} />
                {selectedNews.publishedAt}
              </span>
            </div>
            <span className="bg-brand-50 text-brand-700 px-2 py-0.5 rounded text-[11px] font-sans">
              {selectedNews.category?.toUpperCase()}
            </span>
          </div>

          {/* Body Content in Mallanna */}
          <div className={`font-mallanna text-slate-800 ${getFontSizeClass()} leading-relaxed space-y-4 whitespace-pre-line py-2`}>
            {selectedNews.content}
          </div>

          {/* Bottom Feedback & IT Rules Grievance Prompt */}
          <div className="bg-slate-50 rounded-xl p-3.5 border border-slate-200 text-xs font-mallanna flex flex-col sm:flex-row justify-between items-start sm:items-center gap-3">
            <div>
              <p className="text-slate-700 font-medium">ఈ కథనంలో ఏదైనా చట్టపరమైన అభ్యంతరం ఉందా?</p>
              <p className="text-slate-500 text-[11px]">ఐటీ నిబంధనలు 2021 ప్రకారం గ్రీవెన్స్ అధికారికి నివేదించండి.</p>
            </div>
            <button
              onClick={() => {
                setSelectedNews(null);
                setIsGrievanceOpen(true);
              }}
              className="bg-white border border-brand-300 text-brand-700 hover:bg-brand-50 px-3 py-1.5 rounded-lg font-ramabhadra text-xs flex items-center gap-1.5 shadow-sm"
            >
              <ShieldAlert size={14} className="text-brand-600" />
              ఫిర్యాదు చేయండి
            </button>
          </div>
        </div>

        {/* Modal Sticky Bottom Action Bar */}
        <div className="sticky bottom-0 bg-white border-t border-slate-200 px-4 py-3 flex items-center justify-between gap-3">
          <div className="flex items-center gap-3 font-mallanna">
            {/* Like */}
            <button
              onClick={() => toggleLike(selectedNews.id)}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg border transition ${
                isLiked 
                  ? "bg-rose-50 border-rose-200 text-rose-600 font-bold" 
                  : "border-slate-200 text-slate-700 hover:bg-slate-50"
              }`}
            >
              <Heart size={18} className={isLiked ? "fill-rose-500 stroke-rose-500" : ""} />
              <span>{selectedNews.likesCount || 0}</span>
            </button>

            {/* Bookmark */}
            <button
              onClick={() => toggleBookmark(selectedNews.id)}
              className={`p-2 rounded-lg border transition ${
                isBookmarked 
                  ? "bg-brand-50 border-brand-200 text-brand-600" 
                  : "border-slate-200 text-slate-700 hover:bg-slate-50"
              }`}
              title="సేవ్ చేసుకోండి"
            >
              <Bookmark size={18} className={isBookmarked ? "fill-brand-600 stroke-brand-600" : ""} />
            </button>
          </div>

          {/* Share Buttons */}
          <div className="flex items-center gap-2">
            <button
              onClick={handleCopyLink}
              className="p-2 rounded-lg border border-slate-200 hover:bg-slate-50 text-slate-600 text-xs flex items-center gap-1"
              title="లింక్ కాపీ చేయండి"
            >
              {copied ? <Check size={16} className="text-emerald-600" /> : <Copy size={16} />}
              <span className="hidden sm:inline font-mallanna">లింక్</span>
            </button>

            <button
              onClick={handleWhatsAppShare}
              className="bg-emerald-600 hover:bg-emerald-700 text-white px-3.5 py-1.5 rounded-lg text-xs font-ramabhadra flex items-center gap-1.5 shadow-sm transition"
            >
              <Share2 size={16} />
              వాట్సాప్‌లో షేర్
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
