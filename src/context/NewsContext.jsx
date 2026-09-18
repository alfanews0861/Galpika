import React, { createContext, useContext, useState, useEffect } from "react";
import { INITIAL_NEWS } from "../data/initialNews";
import { 
  collection, 
  query, 
  orderBy, 
  onSnapshot, 
  addDoc, 
  serverTimestamp,
  updateDoc,
  doc,
  increment 
} from "firebase/firestore";
import { db, isLiveFirebaseConfigured } from "../firebase";

const NewsContext = createContext(null);

const STORAGE_NEWS_KEY = "galpika_news_articles";
const STORAGE_LIKES_KEY = "galpika_user_likes";
const STORAGE_BOOKMARKS_KEY = "galpika_user_bookmarks";

export const NewsProvider = ({ children }) => {
  const [newsList, setNewsList] = useState([]);
  const [activeCategory, setActiveCategory] = useState("all");
  const [activeDistrict, setActiveDistrict] = useState("all");
  const [searchQuery, setSearchQuery] = useState("");
  const [fontSizeStep, setFontSizeStep] = useState(0); // -1, 0, 1, 2 (Font scale for Telugu text)
  const [selectedNews, setSelectedNews] = useState(null);
  
  // Modals
  const [isPublishOpen, setIsPublishOpen] = useState(false);
  const [isGrievanceOpen, setIsGrievanceOpen] = useState(false);
  const [selectedPolicy, setSelectedPolicy] = useState(null);

  // User interactions
  const [likedIds, setLikedIds] = useState(() => {
    try {
      return JSON.parse(localStorage.getItem(STORAGE_LIKES_KEY) || "[]");
    } catch {
      return [];
    }
  });

  const [bookmarkedIds, setBookmarkedIds] = useState(() => {
    try {
      return JSON.parse(localStorage.getItem(STORAGE_BOOKMARKS_KEY) || "[]");
    } catch {
      return [];
    }
  });

  // Load News from Firestore or Local Storage / Mock
  useEffect(() => {
    if (isLiveFirebaseConfigured && db) {
      try {
        const q = query(collection(db, "news"), orderBy("createdAt", "desc"));
        const unsubscribe = onSnapshot(q, (snapshot) => {
          if (!snapshot.empty) {
            const fetched = snapshot.docs.map((d) => ({ id: d.id, ...d.data() }));
            setNewsList(fetched);
          } else {
            // First time - fallback to initial news
            setNewsList(INITIAL_NEWS);
          }
        }, (error) => {
          console.warn("Firestore listener error, using local fallback", error);
          loadLocalNews();
        });
        return () => unsubscribe();
      } catch (err) {
        console.warn("Firestore connection error", err);
        loadLocalNews();
      }
    } else {
      loadLocalNews();
    }
  }, []);

  const loadLocalNews = () => {
    const saved = localStorage.getItem(STORAGE_NEWS_KEY);
    if (saved) {
      try {
        setNewsList(JSON.parse(saved));
        return;
      } catch (e) {
        console.error("Local storage load error", e);
      }
    }
    setNewsList(INITIAL_NEWS);
    localStorage.setItem(STORAGE_NEWS_KEY, JSON.stringify(INITIAL_NEWS));
  };

  // Add / Publish News
  const addNews = async (articleData, author) => {
    const newArticle = {
      id: "galpika-" + Date.now(),
      title: articleData.title,
      summary: articleData.summary || articleData.content.slice(0, 120) + "...",
      content: articleData.content,
      category: articleData.category || "politics",
      district: articleData.district || "హైదరాబాద్",
      imageUrl: articleData.imageUrl || "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?auto=format&fit=crop&w=800&q=80",
      authorName: author?.displayName || "గల్పిక విలేకరి",
      authorRole: author?.role || "reporter",
      isSatire: true,
      publishedAt: "ఇప్పుడే ప్రచురితం",
      likesCount: 0,
      viewsCount: 1,
      sharesCount: 0,
      badge: author?.role === "admin" ? "ఎడిటర్స్ ఛాయిస్" : "రిపోర్టర్ కథనం"
    };

    if (isLiveFirebaseConfigured && db) {
      try {
        await addDoc(collection(db, "news"), {
          ...newArticle,
          createdAt: serverTimestamp()
        });
      } catch (e) {
        console.warn("Firestore news add failed, storing locally", e);
      }
    }

    const updated = [newArticle, ...newsList];
    setNewsList(updated);
    localStorage.setItem(STORAGE_NEWS_KEY, JSON.stringify(updated));
    return newArticle;
  };

  // Toggle Like
  const toggleLike = async (newsId) => {
    const isLiked = likedIds.includes(newsId);
    const newLikes = isLiked 
      ? likedIds.filter(id => id !== newsId)
      : [...likedIds, newsId];
    
    setLikedIds(newLikes);
    localStorage.setItem(STORAGE_LIKES_KEY, JSON.stringify(newLikes));

    const updatedList = newsList.map(item => {
      if (item.id === newsId) {
        return {
          ...item,
          likesCount: isLiked ? Math.max(0, (item.likesCount || 0) - 1) : (item.likesCount || 0) + 1
        };
      }
      return item;
    });

    setNewsList(updatedList);
    localStorage.setItem(STORAGE_NEWS_KEY, JSON.stringify(updatedList));

    if (isLiveFirebaseConfigured && db) {
      try {
        const itemRef = doc(db, "news", newsId);
        await updateDoc(itemRef, {
          likesCount: increment(isLiked ? -1 : 1)
        });
      } catch (err) {
        // quiet ignore for mock/local
      }
    }
  };

  // Toggle Bookmark
  const toggleBookmark = (newsId) => {
    const isBookmarked = bookmarkedIds.includes(newsId);
    const newBookmarks = isBookmarked
      ? bookmarkedIds.filter(id => id !== newsId)
      : [...bookmarkedIds, newsId];

    setBookmarkedIds(newBookmarks);
    localStorage.setItem(STORAGE_BOOKMARKS_KEY, JSON.stringify(newBookmarks));
  };

  // Font size scale class
  const getFontSizeClass = () => {
    switch (fontSizeStep) {
      case -1: return "text-sm";
      case 1: return "text-lg";
      case 2: return "text-xl";
      default: return "text-base";
    }
  };

  return (
    <NewsContext.Provider
      value={{
        newsList,
        activeCategory,
        setActiveCategory,
        activeDistrict,
        setActiveDistrict,
        searchQuery,
        setSearchQuery,
        fontSizeStep,
        setFontSizeStep,
        getFontSizeClass,
        selectedNews,
        setSelectedNews,
        likedIds,
        toggleLike,
        bookmarkedIds,
        toggleBookmark,
        addNews,
        isPublishOpen,
        setIsPublishOpen,
        isGrievanceOpen,
        setIsGrievanceOpen,
        selectedPolicy,
        setSelectedPolicy
      }}
    >
      {children}
    </NewsContext.Provider>
  );
};

export const useNews = () => useContext(NewsContext);
