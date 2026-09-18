package com.alfanewgen.galpika.ui.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
fun NewsScreen(
    newsRepository: NewsRepository,
    initialCategory: String = "అన్ని",
    onArticleClick: (NewsArticle) -> Unit,
    onShareArticle: (NewsArticle) -> Unit
) {
    val categories = listOf(
        "అన్ని",
        "రాజకీయ వ్యంగ్యం",
        "సినిమా & వినోదం",
        "వైరల్ & ట్రెండింగ్",
        "క్రీడా సెటైర్లు",
        "కార్టూన్లు & మీమ్స్",
        "సమాజం & వింతలు"
    )

    var selectedCategory by remember { mutableStateOf(initialCategory) }
    var searchQuery by remember { mutableStateOf("") }

    val allArticles by newsRepository.getNewsFeed(category = selectedCategory).collectAsState(initial = emptyList())

    val filteredArticles = remember(allArticles, searchQuery) {
        if (searchQuery.isBlank()) {
            allArticles
        } else {
            allArticles.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                it.content.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text(
                    text = "వార్తలలో శోధించండి...",
                    fontFamily = MallannaFont,
                    fontSize = 15.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "శోధించండి",
                    tint = PrimaryBlue
                )
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = CardBorder
            )
        )

        // Category Filter Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                val selected = cat == selectedCategory
                FilterChip(
                    selected = selected,
                    onClick = { selectedCategory = cat },
                    label = {
                        Text(
                            text = cat,
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

        Spacer(modifier = Modifier.height(8.dp))

        // Articles List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredArticles, key = { it.id }) { article ->
                NewsCard(
                    article = article,
                    onClick = { onArticleClick(article) },
                    onShare = { onShareArticle(article) }
                )
            }
        }
    }
}
