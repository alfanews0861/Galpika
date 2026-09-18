package com.alfanewgen.galpika.ui.screens.legal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
fun TermsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "నిబంధనలు & షరతులు (Terms)",
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
            Text(
                text = "గల్పిక వినియోగ నిబంధనలు (Terms of Service)",
                fontFamily = RamabhadraFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PrimaryBlue
            )

            Text(
                text = "ఈ అప్లికేషన్‌ను డౌన్‌లోడ్ చేసి వినియోగిస్తున్న ప్రతి ఒక్కరూ ఈ కింది నిబంధనలను అంగీకరించినట్లుగా భావించబడుతుంది.",
                fontFamily = MallannaFont,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            )

            Text(
                text = "1. కంటెంట్ కాపీరైట్",
                fontFamily = RamabhadraFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = PrimaryBlue
            )
            Text(
                text = "గల్పిక లోని అన్ని వ్యంగ్య రచనలు, శీర్షికలు మరియు కంటెంట్ గల్పిక టీమ్‌కు చెందిన మేధో సంపత్తి. సరైన క్రెడిట్స్ లేకుండా వాణిజ్యపరంగా పునర్ముద్రించడం లేదా మార్ఫింగ్ చేయడం నిషిద్ధం.",
                fontFamily = MallannaFont,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            )

            Text(
                text = "2. వినియోగదారుల ప్రవర్తన",
                fontFamily = RamabhadraFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = PrimaryBlue
            )
            Text(
                text = "యాప్‌లో హింసను ప్రేరేపించే, దేశ సమగ్రతకు భంగం కలిగించే లేదా అసభ్యకరమైన కామెంట్లు, పోస్టులు పెట్టడం పూర్తిగా నిషేధం. అలాంటి ఖాతాలను అడ్మిన్ తక్షణమే రద్దు చేసే హక్కు కలిగి ఉంటారు.",
                fontFamily = MallannaFont,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            )
        }
    }
}
