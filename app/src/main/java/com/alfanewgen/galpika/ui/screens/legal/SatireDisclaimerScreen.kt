package com.alfanewgen.galpika.ui.screens.legal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfanewgen.galpika.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SatireDisclaimerScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "వ్యంగ్య ప్రకటన & ఉద్దేశం (Disclaimer)",
                        fontFamily = RamabhadraFont,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "వెనుకకు", tint = Color.White)
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
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = AccentAmber.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ముఖ్యమైన చట్టబద్ధ వివరణ (Satirical Nature)",
                        fontFamily = RamabhadraFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "‘గల్పిక’ (Galpika) అనేది స్వచ్ఛమైన తెలుగు వ్యంగ్య, హాస్య మరియు పేరడీ డిజిటల్ మాధ్యమం. ఇందులో ప్రచురితమయ్యే వార్తా కథనాలు సమాజంలో ఆలోచన రేకెత్తించడానికి, నవ్వులు పంచడానికి రూపొందించబడిన కల్పితాలు.",
                        fontFamily = MallannaFont,
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        color = TextSecondary
                    )
                }
            }

            Text(
                text = "1. నిజజీవిత వ్యక్తులతో సంబంధం లేదు",
                fontFamily = RamabhadraFont,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = PrimaryBlue
            )
            Text(
                text = "ఈ అప్లికేషన్‌లో పేర్కొనబడే పాత్రలు, పేర్లు, ప్రకటనలు, కథనాలు కేవలం కల్పితాలు మరియు వ్యంగ్యాస్త్రాలు మాత్రమే. ఏ నిజమైన వ్యక్తిని, రాజకీయనాయకుడిని, సంస్థను లేదా వర్గాన్ని కించపరచడం మా ఉద్దేశం కాదు. నిజజీవిత సంఘటనలతో పోలిక ఉంటే అది కేవలం కాకతాళీయం మాత్రమే.",
                fontFamily = MallannaFont,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            )

            Text(
                text = "2. భారత రాజ్యాంగం ఆర్టికల్ 19(1)(a) రక్షణ",
                fontFamily = RamabhadraFont,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = PrimaryBlue
            )
            Text(
                text = "భారత రాజ్యాంగం ప్రసాదించిన భావప్రకటనా స్వేచ్ఛ మరియు సృజనాత్మక వ్యంగ్య రచనల పరిధికి లోబడి ఈ వేదిక నిర్వహించబడుతోంది. వినోదం, సమాజ శ్రేయస్సు మరియు హాస్య స్పృహను పెంపొందించడమే దీని పరమావధి.",
                fontFamily = MallannaFont,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            )

            Text(
                text = "3. పాఠకులకు సూచన",
                fontFamily = RamabhadraFont,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = PrimaryBlue
            )
            Text(
                text = "పాఠకులు ఈ కథనాలను వాస్తవ సమాచారంగా భావించి ఇతరులకు భయాందోళనలు లేదా అపార్థాలు కలిగించేలా ప్రచారం చేయరాదు. ఇది కేవలం వినోదం కోసమేనని గ్రహించగలరు.",
                fontFamily = MallannaFont,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            )
        }
    }
}
