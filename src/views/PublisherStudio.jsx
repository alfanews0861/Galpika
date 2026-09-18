import React, { useState } from "react";
import { 
  X, 
  PenSquare, 
  Image as ImageIcon, 
  Send, 
  Eye, 
  CheckCircle2, 
  Sparkles,
  ShieldCheck
} from "lucide-react";
import { useAuth } from "../context/AuthContext";
import { useNews } from "../context/NewsContext";
import { NEWS_CATEGORIES, TELUGU_STATES } from "../data/teluguDistricts";

export const PublisherStudio = () => {
  const { user, isAdmin, isReporter } = useAuth();
  const { isPublishOpen, setIsPublishOpen, addNews } = useNews();

  const [activeTab, setActiveTab] = useState("editor"); // 'editor' | 'preview'
  const [title, setTitle] = useState("");
  const [summary, setSummary] = useState("");
  const [content, setContent] = useState("");
  const [category, setCategory] = useState("politics");
  const [district, setDistrict] = useState("హైదరాబాద్");
  const [imageUrl, setImageUrl] = useState("https://images.unsplash.com/photo-1541872703-74c5e44368f9?auto=format&fit=crop&w=800&q=80");
  const [submitting, setSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);

  if (!isPublishOpen) return null;

  const sampleImages = [
    { label: "రాజకీయం", url: "https://images.unsplash.com/photo-1541872703-74c5e44368f9?auto=format&fit=crop&w=800&q=80" },
    { label: "బస్సు/రోడ్డు", url: "https://images.unsplash.com/photo-1570125909232-eb263c188f7e?auto=format&fit=crop&w=800&q=80" },
    { label: "సినిమా", url: "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?auto=format&fit=crop&w=800&q=80" },
    { label: "టెక్నాలజీ", url: "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=800&q=80" },
    { label: "ట్రాఫిక్/సిటీ", url: "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?auto=format&fit=crop&w=800&q=80" }
  ];

  const handlePublish = async (e) => {
    e.preventDefault();
    if (!title || !content) return;

    setSubmitting(true);
    await addNews({
      title,
      summary: summary || content.slice(0, 140) + "...",
      content,
      category,
      district,
      imageUrl
    }, user);

    setSubmitting(false);
    setSuccess(true);
    setTimeout(() => {
      setSuccess(false);
      setIsPublishOpen(false);
      setTitle("");
      setSummary("");
      setContent("");
    }, 1200);
  };

  return (
    <div className="fixed inset-0 z-50 bg-slate-900/60 backdrop-blur-sm flex items-center justify-center p-3 sm:p-4 overflow-y-auto">
      <div 
        className="bg-white w-full max-w-3xl rounded-2xl shadow-2xl overflow-hidden flex flex-col max-h-[90vh] animate-in fade-in zoom-in-95 duration-200 my-auto"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Header */}
        <div className="bg-brand-900 text-white px-5 py-3.5 flex items-center justify-between">
          <div className="flex items-center gap-2">
            <div className="w-7 h-7 rounded-lg bg-brand-700 flex items-center justify-center text-amber-300">
              <PenSquare size={16} />
            </div>
            <div>
              <h2 className="font-ramabhadra text-base sm:text-lg leading-none">
                గల్పిక న్యూస్ స్టూడియో (News Studio)
              </h2>
              <p className="text-[11px] text-brand-300 font-mallanna mt-0.5">
                {user?.displayName} ({isAdmin ? "ప్రధాన అడ్మిన్" : "అనుమతి పొందిన రిపోర్టర్"})
              </p>
            </div>
          </div>

          <div className="flex items-center gap-2">
            {/* Tab switch: Editor vs Preview */}
            <div className="flex bg-brand-800 rounded-lg p-0.5 text-xs font-ramabhadra">
              <button
                onClick={() => setActiveTab("editor")}
                className={`px-3 py-1 rounded-md transition ${activeTab === "editor" ? "bg-brand-600 text-white shadow" : "text-brand-300"}`}
              >
                రాయండి
              </button>
              <button
                onClick={() => setActiveTab("preview")}
                className={`px-3 py-1 rounded-md transition flex items-center gap-1 ${activeTab === "preview" ? "bg-brand-600 text-white shadow" : "text-brand-300"}`}
              >
                <Eye size={12} />
                ప్రివ్యూ
              </button>
            </div>

            <button
              onClick={() => setIsPublishOpen(false)}
              className="p-1 rounded-full text-brand-300 hover:text-white hover:bg-brand-800 transition"
            >
              <X size={20} />
            </button>
          </div>
        </div>

        {/* Studio Body */}
        <div className="p-5 overflow-y-auto flex-1 font-mallanna text-sm">
          {success ? (
            <div className="text-center py-12 space-y-3">
              <div className="w-16 h-16 bg-emerald-100 text-emerald-600 rounded-full flex items-center justify-center mx-auto">
                <CheckCircle2 size={36} />
              </div>
              <h3 className="text-2xl font-bold font-ramabhadra text-slate-900">
                వార్త విజయవంతంగా ప్రచురించబడింది!
              </h3>
              <p className="text-slate-600 font-mallanna">
                మీ వ్యంగ్య కథనం గల్పిక మరియు వార్తలు ట్యాబ్‌లలో పాఠకులకు కనిపిస్తుంది.
              </p>
            </div>
          ) : activeTab === "editor" ? (
            /* News Authoring Form */
            <form onSubmit={handlePublish} className="space-y-4">
              {/* Title input (Ramabhadra) */}
              <div>
                <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                  వార్త శీర్షిక (Headline in Ramabhadra Font) *
                </label>
                <input
                  type="text"
                  required
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}
                  placeholder="ఉదా: వర్షానికి రోడ్డు కొట్టుకుపోవడంతో కాంట్రాక్టర్‌కు అవార్డు..."
                  className="w-full border border-slate-300 rounded-xl px-3.5 py-2.5 text-base font-ramabhadra text-slate-900 placeholder-slate-400 focus:ring-2 focus:ring-brand-500 focus:outline-none"
                />
              </div>

              {/* Category & District Grid */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                <div>
                  <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                    విభాగం (Category) *
                  </label>
                  <select
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                    className="w-full border border-slate-300 rounded-xl px-3 py-2 bg-white text-sm font-ramabhadra focus:ring-2 focus:ring-brand-500 focus:outline-none"
                  >
                    {NEWS_CATEGORIES.filter(c => c.id !== "all").map(c => (
                      <option key={c.id} value={c.id}>{c.name}</option>
                    ))}
                  </select>
                </div>

                <div>
                  <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                    ప్రాంతం / జిల్లా (District for Local Tab) *
                  </label>
                  <select
                    value={district}
                    onChange={(e) => setDistrict(e.target.value)}
                    className="w-full border border-slate-300 rounded-xl px-3 py-2 bg-white text-sm font-mallanna focus:ring-2 focus:ring-brand-500 focus:outline-none"
                  >
                    {TELUGU_STATES.filter(d => d.id !== "all").map(d => (
                      <option key={d.id} value={d.name}>{d.name}</option>
                    ))}
                  </select>
                </div>
              </div>

              {/* Summary / Excerpt */}
              <div>
                <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                  సంక్షిప్త వివరణ (Summary - 1-2 వాక్యాలు)
                </label>
                <input
                  type="text"
                  value={summary}
                  onChange={(e) => setSummary(e.target.value)}
                  placeholder="కార్డ్‌పై కనిపించే చిన్న సారాంశం..."
                  className="w-full border border-slate-300 rounded-xl px-3 py-2 text-sm font-mallanna focus:ring-2 focus:ring-brand-500 focus:outline-none"
                />
              </div>

              {/* Content in Mallanna */}
              <div>
                <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                  పూర్తి కథనం (Full Article Body in Mallanna Font) *
                </label>
                <textarea
                  rows={5}
                  required
                  value={content}
                  onChange={(e) => setContent(e.target.value)}
                  placeholder="మీ వ్యంగ్య కథనాన్ని ఇక్కడ రాయండి..."
                  className="w-full border border-slate-300 rounded-xl px-3.5 py-2.5 text-base font-mallanna text-slate-800 focus:ring-2 focus:ring-brand-500 focus:outline-none leading-relaxed"
                />
              </div>

              {/* Image URL & Sample Picker */}
              <div>
                <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1 flex items-center justify-between">
                  <span>చిత్రం URL (Image URL)</span>
                  <span className="text-[11px] text-slate-500 font-normal">శాంపిల్ ఇమేజ్ ఎంచుకోండి:</span>
                </label>
                <input
                  type="url"
                  value={imageUrl}
                  onChange={(e) => setImageUrl(e.target.value)}
                  placeholder="https://..."
                  className="w-full border border-slate-300 rounded-xl px-3 py-2 text-xs font-mono text-slate-700 focus:ring-2 focus:ring-brand-500 focus:outline-none"
                />
                
                {/* Sample quick buttons */}
                <div className="flex items-center gap-2 mt-1.5 overflow-x-auto pb-1">
                  {sampleImages.map((s, idx) => (
                    <button
                      key={idx}
                      type="button"
                      onClick={() => setImageUrl(s.url)}
                      className={`text-[11px] px-2.5 py-1 rounded-lg border font-mallanna transition shrink-0 ${
                        imageUrl === s.url 
                          ? "bg-brand-100 border-brand-400 text-brand-800 font-bold" 
                          : "bg-slate-50 border-slate-200 text-slate-600 hover:bg-slate-100"
                      }`}
                    >
                      {s.label}
                    </button>
                  ))}
                </div>
              </div>

              {/* Satire statutory badge assurance */}
              <div className="bg-brand-50 border border-brand-200 rounded-xl p-3 text-xs flex items-center gap-2 text-brand-900 font-mallanna">
                <ShieldCheck size={18} className="text-brand-600 shrink-0" />
                <span>
                  ఈ కథనానికి ఆటోమేటిక్‌గా <strong>'గల్పిక వ్యంగ్య కథనం'</strong> చట్టబద్ధమైన నిరాకరణ (Satire Disclaimer) జతచేయబడుతుంది.
                </span>
              </div>

              {/* Submit Button */}
              <div className="pt-2">
                <button
                  type="submit"
                  disabled={submitting}
                  className="w-full bg-brand-600 hover:bg-brand-700 text-white font-ramabhadra py-3 rounded-xl transition shadow flex items-center justify-center gap-2 text-base"
                >
                  <Send size={16} />
                  {submitting ? "ప్రచురింపబడుతోంది..." : "వార్తను వెంటనే ప్రచురించండి"}
                </button>
              </div>
            </form>
          ) : (
            /* Live Preview Mode */
            <div className="space-y-4 max-w-xl mx-auto">
              <div className="bg-amber-50 text-amber-900 p-2.5 rounded-xl border border-amber-200 text-xs font-mallanna flex items-center gap-2">
                <Sparkles size={14} className="text-amber-600" />
                ఇది కార్డ్ పాఠకులకు ఎలా కనిపిస్తుందో చూపే లైవ్ ప్రివ్యూ.
              </div>

              <div className="bg-white rounded-2xl border border-slate-200 shadow-md overflow-hidden">
                <div className="aspect-video w-full overflow-hidden bg-slate-100">
                  <img src={imageUrl} alt="preview" className="w-full h-full object-cover" />
                </div>
                <div className="p-4 space-y-2">
                  <div className="flex items-center justify-between text-xs text-slate-500 font-mallanna">
                    <span className="text-brand-700 font-bold">{user?.displayName || "విలేకరి"}</span>
                    <span>{district}</span>
                  </div>
                  <h3 className="font-ramabhadra text-lg font-bold text-slate-900 leading-snug">
                    {title || "శీర్షిక ఇక్కడ కనిపిస్తుంది..."}
                  </h3>
                  <p className="font-mallanna text-slate-600 text-sm leading-relaxed">
                    {summary || content || "కథన సారాంశం ఇక్కడ కనిపిస్తుంది..."}
                  </p>
                </div>
              </div>

              <button
                onClick={() => setActiveTab("editor")}
                className="w-full bg-slate-100 hover:bg-slate-200 text-slate-700 font-ramabhadra py-2 rounded-xl text-xs"
              >
                &larr; ఎడిటర్‌కి తిరిగి వెళ్లండి
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
