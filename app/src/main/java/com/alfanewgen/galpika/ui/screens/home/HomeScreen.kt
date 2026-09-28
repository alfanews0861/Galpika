package com.alfanewgen.galpika.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfanewgen.galpika.data.model.NewsArticle
import com.alfanewgen.galpika.data.repository.NewsRepository
import com.alfanewgen.galpika.theme.*
import com.alfanewgen.galpika.ui.components.NewsCard

@Composable
fun HomeScreen(
    newsRepository: NewsRepository,
    onArticleClick: (NewsArticle) -> Unit,
    onShareArticle: (NewsArticle) -> Unit
) {
    val articles by newsRepository.getNewsFeed().collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Title
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ముఖ్యాంశాలు (Top Satire)",
                    fontFamily = RamabhadraFont,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
                Text(
                    text = "${articles.size} వార్తలు",
                    fontFamily = MallannaFont,
                    fontSize = 13.sp,
                    color = TextMuted
                )
            }
        }

        // Articles List
        items(articles, key = { it.id }) { article ->
            NewsCard(
                article = article,
                onClick = { onArticleClick(article) },
                onShare = { onShareArticle(article) }
            )
        }
    }
}
