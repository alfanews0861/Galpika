package com.alfanewgen.galpika.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class NewsArticle(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val summary: String = "",
    val category: String = "రాజకీయ వ్యంగ్యం",
    val district: String = "ఆంధ్రప్రదేశ్ & తెలంగాణ",
    val imageUrl: String = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?auto=format&fit=crop&w=800&q=80",
    val isSatire: Boolean = true,
    val authorId: String = "",
    val authorName: String = "గల్పిక ప్రతినిధి",
    val authorRole: String = "reporter",
    val status: String = "published",
    val likesCount: Long = 0,
    val viewsCount: Long = 0,
    @ServerTimestamp
    val createdAt: Date? = null,
    val disclaimer: String = "గమనిక: ఇది పూర్తిగా వ్యంగ్య/హాస్య కల్పిత కథనం మాత్రమే. నిజజీవిత వ్యక్తులు లేదా సంఘటనలతో పోలిక ఉంటే అది కేవలం కాకతాళీయం మాత్రమే (Satirical Fiction)."
)
