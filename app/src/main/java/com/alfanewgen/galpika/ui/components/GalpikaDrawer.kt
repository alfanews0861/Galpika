package com.alfanewgen.galpika.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfanewgen.galpika.theme.*

@Composable
fun GalpikaDrawer(
    onCategoryClick: (String) -> Unit,
    onNavigate: (String) -> Unit,
    onShareApp: () -> Unit,
    onCloseDrawer: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.width(310.dp),
        drawerContainerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryBlue)
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "గల్పిక",
                            fontFamily = RamabhadraFont,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(AccentAmber)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "వ్యంగ్య పోర్టల్",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "తెలుగు వ్యంగ్య & హాస్య వార్తా వేదిక",
                        fontFamily = MallannaFont,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Categories Section
            Text(
                text = "వార్తా విభాగాలు (Categories)",
                fontFamily = RamabhadraFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )

            val categories = listOf(
                "రాజకీయ వ్యంగ్యం" to Icons.Default.Gavel,
                "సినిమా & వినోదం" to Icons.Default.Movie,
                "వైరల్ & ట్రెండింగ్" to Icons.Default.TrendingUp,
                "క్రీడా సెటైర్లు" to Icons.Default.SportsCricket,
                "కార్టూన్లు & మీమ్స్" to Icons.Default.EmojiEmotions,
                "సమాజం & వింతలు" to Icons.Default.Public
            )

            categories.forEach { (catName, icon) ->
                DrawerItem(
                    icon = icon,
                    label = catName,
                    onClick = {
                        onCategoryClick(catName)
                        onCloseDrawer()
                    }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

            // Legal & Policies Section (భారతీయ చట్టాల నిబంధనలు)
            Text(
                text = "చట్టబద్ధ నిబంధనలు & పాలసీలు",
                fontFamily = RamabhadraFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )

            DrawerItem(
                icon = Icons.Default.Warning,
                label = "వ్యంగ్య ప్రకటన & ఉద్దేశం",
                onClick = {
                    onNavigate("disclaimer")
                    onCloseDrawer()
                }
            )

            DrawerItem(
                icon = Icons.Default.SupportAgent,
                label = "ఫిర్యాదుల పరిష్కారం (IT Rules)",
                onClick = {
                    onNavigate("grievance")
                    onCloseDrawer()
                }
            )

            DrawerItem(
                icon = Icons.Default.PrivacyTip,
                label = "గోప్యతా విధానం (Privacy)",
                onClick = {
                    onNavigate("privacy")
                    onCloseDrawer()
                }
            )

            DrawerItem(
                icon = Icons.Default.Description,
                label = "నిబంధనలు & షరతులు (Terms)",
                onClick = {
                    onNavigate("terms")
                    onCloseDrawer()
                }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

            // Actions
            DrawerItem(
                icon = Icons.Default.Share,
                label = "యాప్‌ను షేర్ చేయండి",
                onClick = {
                    onShareApp()
                    onCloseDrawer()
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Version info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "గల్పిక v1.0 • మేడ్ ఇన్ ఇండియా",
                    fontFamily = MallannaFont,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun DrawerItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = PrimaryBlue,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = label,
            fontFamily = MallannaFont,
            fontSize = 15.sp,
            color = TextPrimary
        )
    }
}
