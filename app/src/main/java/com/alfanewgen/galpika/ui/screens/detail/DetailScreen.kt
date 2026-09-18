package com.alfanewgen.galpika.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.alfanewgen.galpika.data.model.NewsArticle
import com.alfanewgen.galpika.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    article: NewsArticle,
    onBack: () -> Unit,
    onShare: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "గల్పిక వార్తా కథనం",
                        fontFamily = RamabhadraFont,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "వెనుకకు",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onShare) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "షేర్ చేయండి",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight)
                .verticalScroll(rememberScrollState())
        ) {
            // Main Image
            AsyncImage(
                model = article.imageUrl,
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(18.dp)) {
                // Category & Satire Badges
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(AccentAmber)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "హాస్య వ్యంగ్యం (Satire)",
                            fontFamily = MallannaFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PrimaryBlue.copy(alpha = 0.1f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = article.category,
                            fontFamily = MallannaFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryBlue
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Headline in Ramabhadra Font
                Text(
                    text = article.title,
                    fontFamily = RamabhadraFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    lineHeight = 34.sp,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Metadata: Reporter and Location
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "✍️ ${article.authorName}",
                        fontFamily = MallannaFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryBlue
                    )
                    Text(
                        text = "📍 ${article.district}",
                        fontFamily = MallannaFont,
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))

                // Full Article Content in Mallanna Font
                Text(
                    text = article.content,
                    fontFamily = MallannaFont,
                    fontSize = 18.sp,
                    lineHeight = 30.sp,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Statutory Satire Disclaimer Box (భారతీయ చట్టాల ప్రకారం స్పష్టమైన వివరణ)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = AccentAmber.copy(alpha = 0.12f)),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(AccentAmber))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "వ్యంగ్య ప్రకటన",
                                tint = AccentAmber
                            )
                            Text(
                                text = "చట్టబద్ధమైన వ్యంగ్య ప్రకటన (Satire Disclaimer)",
                                fontFamily = RamabhadraFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = TextPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = article.disclaimer,
                            fontFamily = MallannaFont,
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Share Button
                Button(
                    onClick = onShare,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ఈ వ్యంగ్య కథనాన్ని మిత్రులతో పంచుకోండి",
                        fontFamily = MallannaFont,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
