package com.alfanewgen.galpika.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfanewgen.galpika.theme.AccentAmber
import com.alfanewgen.galpika.theme.PrimaryBlue
import com.alfanewgen.galpika.theme.RamabhadraFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalpikaTopBar(
    title: String = "గల్పిక",
    onMenuClick: () -> Unit,
    onShareClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    fontFamily = RamabhadraFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(AccentAmber)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "వ్యంగ్య వార్తలు",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "మెనూ",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "షేర్ చేయండి",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryBlue,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}
