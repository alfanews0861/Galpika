import React from "react";
import { 
  Heart, 
  Share2, 
  Bookmark, 
  MapPin, 
  Clock, 
  ShieldCheck, 
  Sparkles 
} from "lucide-react";
import { useNews } from "../context/NewsContext";

export const NewsCard = ({ news, featured = false }) => {
  const { 
    setSelectedNews, 
    likedIds, 
    toggleLike, 
    bookmarkedIds, 
    toggleBookmark,
    getFontSizeClass 
  } = useNews();

  const isLiked = likedIds.includes(news.id);
  const isBookmarked = bookmarkedIds.includes(news.id);

  const handleShare = (e) => {
    e.stopPropagation();
    const shareText = `*${news.title}*\n\nగల్పిక తెలుగు వ్యంగ్య వార్తా వేదికపై చదవండి:\n${window.location.origin}`;
    if (navigator.share) {
      navigator.share({
        title: news.title,
        text: shareText,
        url: window.location.href,
      }).catch(() => {});
    } else {
      const whatsappUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent(shareText)}`;
      window.open(whatsappUrl, "_blank");
    }
  };

  return (
    <article 
      onClick={() => setSelectedNews(news)}
      className={`group bg-white rounded-2xl border border-slate-200/80 hover:border-brand-300 transition-all duration-300 shadow-sm hover:shadow-md cursor-pointer overflow-hidden flex flex-col ${
        featured ? "md:col-span-2 border-brand-200 bg-gradient-to-b from-brand-50/40 to-white" : ""
      }`}
    >
      {/* Card Image */}
      <div className="relative aspect-[16/9] w-full overflow-hidden bg-slate-100">
        <img
          src={news.imageUrl}
          alt={news.title}
          loading="lazy"
          className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
          onError={(e) => {
            e.target.src = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?auto=format&fit=crop&w=800&q=80";
          }}
        />

        {/* Satire Badge */}
        <div className="absolute top-2.5 left-2.5 flex items-center gap-1.5 flex-wrap">
          <span className="bg-brand-900/90 backdrop-blur-md text-amber-300 text-[11px] font-ramabhadra px-2.5 py-1 rounded-full border border-brand-700/50 shadow-sm flex items-center gap-1">
            <Sparkles size={11} className="text-amber-400" />
            వ్యంగ్య కథనం
          </span>

          {news.badge && (
            <span className="bg-brand-600/90 backdrop-blur-md text-white text-[11px] font-mallanna font-medium px-2 py-0.5 rounded-full shadow-sm">
              {news.badge}
            </span>
          )}
        </div>

        {/* District Tag */}
        {news.district && (
          <div className="absolute bottom-2.5 left-2.5">
            <span className="bg-black/60 backdrop-blur-md text-slate-100 text-[11px] font-mallanna px-2 py-0.5 rounded-md flex items-center gap-1">
              <MapPin size={11} className="text-brand-300" />
              {news.district}
            </span>
          </div>
        )}
      </div>

      {/* Content Section */}
      <div className="p-4 sm:p-5 flex-1 flex flex-col justify-between">
        <div>
          {/* Metadata Row */}
          <div className="flex items-center justify-between text-xs text-slate-500 mb-2 font-mallanna">
            <div className="flex items-center gap-1 text-brand-700 font-medium">
              <ShieldCheck size={13} className="text-brand-600" />
              <span>{news.authorName}</span>
              {news.authorRole === "admin" && (
                <span className="text-[10px] bg-brand-100 text-brand-800 px-1.5 py-0.2 rounded font-sans font-bold">
                  ADMIN
                </span>
              )}
            </div>
            <div className="flex items-center gap-1 text-slate-400">
              <Clock size={12} />
              <span>{news.publishedAt}</span>
            </div>
          </div>

          {/* Title in Ramabhadra Font */}
          <h2 className={`font-ramabhadra font-bold text-slate-900 leading-snug tracking-tight group-hover:text-brand-700 transition-colors ${
            featured ? "text-lg sm:text-2xl" : "text-base sm:text-lg"
          } telugu-title-clamp`}>
            {news.title}
          </h2>

          {/* Body/Summary in Mallanna Font */}
          <p className={`font-mallanna text-slate-600 mt-2 telugu-body-clamp ${getFontSizeClass()} leading-relaxed`}>
            {news.summary}
          </p>
        </div>

        {/* Action Row */}
        <div className="pt-4 mt-3 border-t border-slate-100 flex items-center justify-between font-mallanna text-xs text-slate-500">
          <div className="flex items-center gap-3">
            {/* Like Button */}
            <button
              onClick={(e) => {
                e.stopPropagation();
                toggleLike(news.id);
              }}
              className={`flex items-center gap-1.5 transition ${
                isLiked ? "text-rose-600 font-bold" : "hover:text-rose-600"
              }`}
              title="లైక్ చేయండి"
            >
              <Heart 
                size={16} 
                className={isLiked ? "fill-rose-500 stroke-rose-500" : ""} 
              />
              <span className="text-sm">{news.likesCount || 0}</span>
            </button>

            {/* WhatsApp / Share */}
            <button
              onClick={handleShare}
              className="flex items-center gap-1 hover:text-brand-600 transition"
              title="షేర్ చేయండి"
            >
              <Share2 size={16} />
              <span className="hidden xs:inline text-sm">షేర్</span>
            </button>
          </div>

          {/* Bookmark & Read More */}
          <div className="flex items-center gap-2">
            <button
              onClick={(e) => {
                e.stopPropagation();
                toggleBookmark(news.id);
              }}
              className={`p-1.5 rounded-lg hover:bg-slate-100 transition ${
                isBookmarked ? "text-brand-600" : "text-slate-400"
              }`}
              title="సేవ్ చేసుకోండి"
            >
              <Bookmark 
                size={16} 
                className={isBookmarked ? "fill-brand-600 stroke-brand-600" : ""} 
              />
            </button>

            <span className="text-brand-600 font-ramabhadra text-xs font-semibold group-hover:translate-x-0.5 transition-transform flex items-center">
              పూర్తిగా చదవండి &rarr;
            </span>
          </div>
        </div>
      </div>
    </article>
  );
};
