package com.alfanewgen.galpika.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alfanewgen.galpika.R

// Headings Font: Ramabhadra (శీర్షికలకు రామభద్ర ఫాంట్)
val RamabhadraFont = FontFamily(
    Font(R.font.ramabhadra_regular, FontWeight.Normal)
)

// Body / Normal Text Font: Mallanna (సాధారణ పాఠానికి మల్లన్న ఫాంట్)
val MallannaFont = FontFamily(
    Font(R.font.mallanna_regular, FontWeight.Normal)
)

val GalpikaTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = RamabhadraFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 42.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = RamabhadraFont,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = RamabhadraFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = RamabhadraFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = RamabhadraFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = RamabhadraFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = RamabhadraFont,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = RamabhadraFont,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MallannaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.25.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MallannaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = MallannaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MallannaFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MallannaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = MallannaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
)
