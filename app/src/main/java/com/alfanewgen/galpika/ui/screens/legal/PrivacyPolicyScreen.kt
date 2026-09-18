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
fun PrivacyPolicyScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "గోప్యతా విధానం (Privacy Policy)",
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
                text = "గల్పిక గోప్యతా విధానం (DPDP Act & Google Play పాలసీలకు అనుగుణంగా)",
                fontFamily = RamabhadraFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PrimaryBlue
            )

            Text(
                text = "మీ వ్యక్తిగత గోప్యతను గౌరవించడం మా బాధ్యత. భారత డిజిటల్ పర్సనల్ డేటా ప్రొటెక్షన్ యాక్ట్ (DPDP Act, 2023) మరియు గూగుల్ ప్లే స్టోర్ డెవలపర్ నియమాలకు లోబడి ఈ పాలసీ రూపొందించబడింది.",
                fontFamily = MallannaFont,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            )

            Text(
                text = "1. మేము సేకరించే సమాచారం",
                fontFamily = RamabhadraFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = PrimaryBlue
            )
            Text(
                text = "• Google లాగిన్ ద్వారా మీ ప్రాథమిక వివరాలు (పేరు, ఇమెయిల్, ప్రొఫైల్ ఫోటో).\n• మీరు సమర్పించే గ్రీవెన్స్ లేదా ఫిర్యాదు వివరాలు.\n• యాప్ పనితీరు మెరుగుపరచడానికి క్రాష్ లాగ్‌లు మరియు ఫైర్‌బేస్ అనలిటిక్స్ సమాచారం.",
                fontFamily = MallannaFont,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            )

            Text(
                text = "2. సమాచార భద్రత & వినియోగం",
                fontFamily = RamabhadraFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = PrimaryBlue
            )
            Text(
                text = "మేము మీ వ్యక్తిగత సమాచారాన్ని ఎటువంటి మూడవ పక్షాలకు (Third-parties) విక్రయించము. కేవలం మీ గుర్తింపును ధృవీకరించడానికి మరియు అనుమతి పొందిన రిపోర్టర్లకు యాక్సెస్ ఇవ్వడానికి మాత్రమే ఉపయోగిస్తాము. మీ డేటా Google Cloud / Firebase అత్యున్నత రక్షణ ప్రమాణాలతో భద్రపరచబడుతుంది.",
                fontFamily = MallannaFont,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            )

            Text(
                text = "3. సంప్రదించండి",
                fontFamily = RamabhadraFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = PrimaryBlue
            )
            Text(
                text = "గోప్యతా సమస్యలకు సంబంధించి మా ప్రైవసీ ఆఫీసర్‌ను సంప్రదించవచ్చు: privacy@galpika.com",
                fontFamily = MallannaFont,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            )
        }
    }
}
