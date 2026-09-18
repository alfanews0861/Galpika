package com.alfanewgen.galpika.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.alfanewgen.galpika.data.model.NewsArticle
import com.alfanewgen.galpika.theme.*

@Composable
fun NewsCard(
    article: NewsArticle,
    onClick: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // News Thumbnail Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = article.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Satire Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(AccentAmber)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "హాస్య వ్యంగ్యం",
                        fontFamily = MallannaFont,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Category Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(PrimaryBlue.copy(alpha = 0.85f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = article.category,
                        fontFamily = MallannaFont,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                // Headline in Ramabhadra Font
                Text(
                    text = article.title,
                    fontFamily = RamabhadraFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    lineHeight = 26.sp,
                    color = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Summary in Mallanna Font
                Text(
                    text = article.summary.ifBlank { article.content.take(120) + "..." },
                    fontFamily = MallannaFont,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    color = TextSecondary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Footer row: Author, District and Share
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = article.authorName,
                            fontFamily = MallannaFont,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryBlue
                        )
                        Text(
                            text = "📍 ${article.district}",
                            fontFamily = MallannaFont,
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }

                    IconButton(onClick = onShare) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "షేర్ చేయండి",
                            tint = PrimaryBlue
                        )
                    }
                }
            }
        }
    }
}
