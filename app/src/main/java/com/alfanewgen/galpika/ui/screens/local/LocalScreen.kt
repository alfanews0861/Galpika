package com.alfanewgen.galpika.ui.screens.local

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfanewgen.galpika.data.model.NewsArticle
import com.alfanewgen.galpika.data.repository.NewsRepository
import com.alfanewgen.galpika.theme.*
import com.alfanewgen.galpika.ui.components.NewsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalScreen(
    newsRepository: NewsRepository,
    onArticleClick: (NewsArticle) -> Unit,
    onShareArticle: (NewsArticle) -> Unit
) {
    val districts = listOf("అన్ని", "హైదరాబాద్", "విశాఖపట్నం", "విజయవాడ", "తిరుపతి", "వరంగల్", "గుంటూరు", "కర్నూలు")
    var selectedDistrict by remember { mutableStateOf("అన్ని") }

    val articles by newsRepository.getNewsFeed(district = selectedDistrict).collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        // District Filter Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(districts) { district ->
                val selected = district == selectedDistrict
                FilterChip(
                    selected = selected,
                    onClick = { selectedDistrict = district },
                    label = {
                        Text(
                            text = district,
                            fontFamily = MallannaFont,
                            fontSize = 14.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryBlue,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // Articles List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "స్థానిక వ్యంగ్య విశేషాలు: $selectedDistrict",
                    fontFamily = RamabhadraFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }

            if (articles.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(
                            text = "ఈ ప్రాంతంలో ప్రస్తుతానికి ఎలాంటి వ్యంగ్య వార్తలు నమోదు కాలేదు.",
                            fontFamily = MallannaFont,
                            fontSize = 15.sp,
                            color = TextMuted
                        )
                    }
                }
            } else {
                items(articles, key = { it.id }) { article ->
                    NewsCard(
                        article = article,
                        onClick = { onArticleClick(article) },
                        onShare = { onShareArticle(article) }
                    )
                }
            }
        }
    }
}
