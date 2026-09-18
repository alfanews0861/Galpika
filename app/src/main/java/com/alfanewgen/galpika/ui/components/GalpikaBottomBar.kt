package com.alfanewgen.galpika.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfanewgen.galpika.theme.MallannaFont
import com.alfanewgen.galpika.theme.PrimaryBlue
import com.alfanewgen.galpika.theme.PrimaryBlueLight

sealed class BottomTab(val route: String, val titleTelugu: String, val icon: ImageVector) {
    object Galpika : BottomTab("home", "గల్పిక", Icons.Default.Home)
    object Local : BottomTab("local", "స్థానిక", Icons.Default.LocationOn)
    object Vaarthalu : BottomTab("news", "వార్తలు", Icons.Default.Newspaper)
    object Profile : BottomTab("profile", "ప్రొఫైల్", Icons.Default.Person)
}

val bottomTabs = listOf(
    BottomTab.Galpika,
    BottomTab.Local,
    BottomTab.Vaarthalu,
    BottomTab.Profile
)

@Composable
fun GalpikaBottomBar(
    currentRoute: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        bottomTabs.forEach { tab ->
            val selected = currentRoute == tab.route
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab.route) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.titleTelugu
                    )
                },
                label = {
                    Text(
                        text = tab.titleTelugu,
                        fontFamily = MallannaFont,
                        fontSize = 13.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = PrimaryBlue,
                    indicatorColor = PrimaryBlue,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}
