import React, { useState } from "react";
import { 
  X, 
  ShieldAlert, 
  CheckCircle2, 
  FileText, 
  Mail, 
  Phone, 
  MapPin, 
  Clock 
} from "lucide-react";
import { useNews } from "../context/NewsContext";
import { GRIEVANCE_OFFICER } from "../data/legalPolicies";

export const GrievanceModal = () => {
  const { isGrievanceOpen, setIsGrievanceOpen } = useNews();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    articleTitle: "",
    category: "satire_objection",
    description: "",
  });

  const [submitting, setSubmitting] = useState(false);
  const [ticketId, setTicketId] = useState(null);

  if (!isGrievanceOpen) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    setSubmitting(true);

    // Simulate backend Cloud Function (functions.submitGrievance)
    setTimeout(() => {
      const generatedId = `GLP-GR-${new Date().getFullYear()}-${Math.floor(10000 + Math.random() * 90000)}`;
      setTicketId(generatedId);
      setSubmitting(false);
    }, 600);
  };

  const handleReset = () => {
    setTicketId(null);
    setFormData({
      name: "",
      email: "",
      phone: "",
      articleTitle: "",
      category: "satire_objection",
      description: "",
    });
    setIsGrievanceOpen(false);
  };

  return (
    <div className="fixed inset-0 z-50 bg-slate-900/60 backdrop-blur-sm flex items-center justify-center p-3 sm:p-4 overflow-y-auto">
      <div 
        className="bg-white w-full max-w-lg rounded-2xl shadow-2xl overflow-hidden animate-in fade-in zoom-in-95 duration-200 my-auto"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Header */}
        <div className="bg-brand-900 text-white p-4 flex items-center justify-between">
          <div className="flex items-center gap-2">
            <ShieldAlert size={20} className="text-amber-400" />
            <div>
              <h2 className="font-ramabhadra text-base sm:text-lg leading-none">
                ఫిర్యాదుల పరిష్కార వేదిక (Grievance Cell)
              </h2>
              <p className="text-[11px] text-brand-200 font-mallanna mt-0.5">
                భారతీయ ఐటీ నిబంధనలు, 2021 (డిజిటల్ మీడియా నియమావళి) ప్రకారం
              </p>
            </div>
          </div>
          <button
            onClick={handleReset}
            className="p-1 rounded-full text-brand-200 hover:text-white hover:bg-brand-800 transition"
          >
            <X size={20} />
          </button>
        </div>

        {/* Content */}
        <div className="p-5 max-h-[80vh] overflow-y-auto font-mallanna text-sm">
          {ticketId ? (
            /* Success confirmation screen */
            <div className="text-center py-6 space-y-4">
              <div className="w-16 h-16 bg-emerald-100 text-emerald-600 rounded-full flex items-center justify-center mx-auto shadow-inner">
                <CheckCircle2 size={36} />
              </div>
              <div>
                <h3 className="text-xl font-bold font-ramabhadra text-slate-900">
                  ఫిర్యాదు స్వీకరించబడింది!
                </h3>
                <p className="text-slate-600 mt-1">
                  మీ ఫిర్యాదు విజయవంతంగా నమోదు చేయబడింది.
                </p>
              </div>

              <div className="bg-brand-50 border border-brand-200 rounded-xl p-4 text-left font-mallanna space-y-2">
                <div className="flex justify-between items-center border-b border-brand-200 pb-2">
                  <span className="text-xs text-brand-700 font-medium">ఫిర్యాదు టికెట్ ఐడీ:</span>
                  <span className="font-mono font-bold text-brand-900 bg-white px-2 py-0.5 rounded border border-brand-300">
                    {ticketId}
                  </span>
                </div>
                <p className="text-xs text-slate-600 leading-relaxed pt-1">
                  • చట్ట ప్రకారం <strong>24 గంటల్లో</strong> మీ ఇమెయిల్‌కు అధికారిక రసీదు పంపబడుతుంది.<br />
                  • <strong>15 రోజుల్లోగా</strong> మా గ్రీవెన్స్ ఆఫీసర్ ఈ అంశంపై నిర్ణయం తీసుకుంటారు.
                </p>
              </div>

              <button
                onClick={handleReset}
                className="w-full bg-brand-600 hover:bg-brand-700 text-white font-ramabhadra py-2.5 rounded-xl transition shadow-md"
              >
                సరే, ముగించు
              </button>
            </div>
          ) : (
            /* Grievance form */
            <form onSubmit={handleSubmit} className="space-y-4">
              {/* Grievance Officer details pill */}
              <div className="bg-slate-50 border border-slate-200 rounded-xl p-3 text-xs text-slate-700 space-y-1">
                <p className="font-bold font-ramabhadra text-brand-900 flex items-center gap-1.5">
                  <FileText size={14} className="text-brand-600" />
                  గ్రీవెన్స్ ఆఫీసర్ వివరాలు:
                </p>
                <p className="text-slate-800 font-medium">{GRIEVANCE_OFFICER.name} ({GRIEVANCE_OFFICER.designation})</p>
                <div className="flex items-center gap-4 text-slate-500 pt-1 text-[11px]">
                  <span className="flex items-center gap-1"><Mail size={12} /> {GRIEVANCE_OFFICER.email}</span>
                  <span className="flex items-center gap-1"><Clock size={12} /> 24 గంటల అక్నాలెడ్జ్‌మెంట్</span>
                </div>
              </div>

              {/* Name */}
              <div>
                <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                  ఫిర్యాదుదారు పేరు *
                </label>
                <input
                  type="text"
                  required
                  value={formData.name}
                  onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                  placeholder="మీ పూర్తి పేరు"
                  className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:ring-2 focus:ring-brand-500 focus:outline-none"
                />
              </div>

              {/* Email & Phone */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                <div>
                  <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                    ఇమెయిల్ చిరునామా *
                  </label>
                  <input
                    type="email"
                    required
                    value={formData.email}
                    onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                    placeholder="name@example.com"
                    className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:ring-2 focus:ring-brand-500 focus:outline-none"
                  />
                </div>
                <div>
                  <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                    ఫోన్ నంబర్
                  </label>
                  <input
                    type="tel"
                    value={formData.phone}
                    onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
                    placeholder="9876543210"
                    className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:ring-2 focus:ring-brand-500 focus:outline-none"
                  />
                </div>
              </div>

              {/* Article Title or URL */}
              <div>
                <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                  ఆక్షేపణ ఉన్న కథనం శీర్షిక లేదా లింక్ *
                </label>
                <input
                  type="text"
                  required
                  value={formData.articleTitle}
                  onChange={(e) => setFormData({ ...formData, articleTitle: e.target.value })}
                  placeholder="వార్త శీర్షిక లేదా లింక్ ఇక్కడ ఇవ్వండి"
                  className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:ring-2 focus:ring-brand-500 focus:outline-none"
                />
              </div>

              {/* Category of Objection */}
              <div>
                <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                  ఫిర్యాదు రకం (Nature of Objection)
                </label>
                <select
                  value={formData.category}
                  onChange={(e) => setFormData({ ...formData, category: e.target.value })}
                  className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:ring-2 focus:ring-brand-500 focus:outline-none bg-white font-mallanna"
                >
                  <option value="satire_objection">వ్యంగ్యం/హాస్యంపై అభ్యంతరం</option>
                  <option value="defamation">వ్యక్తిగత పరువునష్టం ఆరోపణ</option>
                  <option value="copyright">కాపీరైట్ ఉల్లంఘన</option>
                  <option value="inappropriate">అనుచిత పదజాలం లేదా అసత్య ప్రచారం</option>
                  <option value="other">ఇతర చట్టపరమైన సమస్య</option>
                </select>
              </div>

              {/* Description */}
              <div>
                <label className="block text-xs font-bold font-ramabhadra text-slate-800 mb-1">
                  వివరణ (మీ అభ్యంతరాన్ని స్పష్టంగా వివరించండి) *
                </label>
                <textarea
                  rows={3}
                  required
                  value={formData.description}
                  onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                  placeholder="సదరు కథనంలో ఏ అంశం చట్టానికి లేదా నియమావళికి విరుద్ధంగా ఉందో వివరించండి..."
                  className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:ring-2 focus:ring-brand-500 focus:outline-none"
                />
              </div>

              <div className="pt-2">
                <button
                  type="submit"
                  disabled={submitting}
                  className="w-full bg-brand-800 hover:bg-brand-900 text-white font-ramabhadra py-2.5 rounded-xl transition shadow flex items-center justify-center gap-2"
                >
                  {submitting ? "నమోదు అవుతోంది..." : "ఫిర్యాదును సమర్పించండి"}
                </button>
              </div>
            </form>
          )}
        </div>
      </div>
    </div>
  );
};
