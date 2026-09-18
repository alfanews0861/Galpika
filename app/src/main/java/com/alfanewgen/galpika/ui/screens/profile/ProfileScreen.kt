package com.alfanewgen.galpika.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.alfanewgen.galpika.data.model.UserProfile
import com.alfanewgen.galpika.data.repository.AuthRepository
import com.alfanewgen.galpika.theme.*

@Composable
fun ProfileScreen(
    authRepository: AuthRepository,
    onGoogleSignInClick: () -> Unit,
    onPublishNewsClick: () -> Unit,
    onNavigateToPolicy: (String) -> Unit
) {
    val currentUser by authRepository.currentUser.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (currentUser != null) {
                    val user = currentUser!!
                    if (user.photoUrl.isNotBlank()) {
                        AsyncImage(
                            model = user.photoUrl,
                            contentDescription = user.displayName,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.displayName.take(1),
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = user.displayName,
                        fontFamily = RamabhadraFont,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Text(
                        text = user.email,
                        fontFamily = MallannaFont,
                        fontSize = 14.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Role Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                when {
                                    user.isAdmin -> PrimaryBlue
                                    user.isReporterOrAdmin -> AccentAmber
                                    else -> SurfaceVariant
                                }
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "హోదా: ${user.roleDisplayTelugu}",
                            fontFamily = MallannaFont,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (user.isAdmin) Color.White else TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign Out Button
                    OutlinedButton(
                        onClick = { authRepository.signOut() },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusRed),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "లాగౌట్ అవ్వండి", fontFamily = MallannaFont, fontSize = 15.sp)
                    }

                } else {
                    // Not Logged In State
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(SurfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "గల్పిక కు స్వాగతం!",
                        fontFamily = RamabhadraFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )

                    Text(
                        text = "వార్తలను రాయడానికి మరియు ప్రతిస్పందించడానికి లాగిన్ అవ్వండి.",
                        fontFamily = MallannaFont,
                        fontSize = 14.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onGoogleSignInClick,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Login, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "గూగుల్ తో లాగిన్ అవ్వండి (Google Login)",
                            fontFamily = MallannaFont,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // News Publishing Section (Visible for Admin and Authorized Reporter)
        val canPublish = currentUser?.isReporterOrAdmin == true
        if (canPublish) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryBlueLight.copy(alpha = 0.08f)),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(PrimaryBlue))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "రిపోర్టర్ / అడ్మిన్ డెస్క్",
                        fontFamily = RamabhadraFont,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "మీరు అధికారికంగా అనుమతి పొందిన రచయిత. సరికొత్త వ్యంగ్య లేదా హాస్య కథనాన్ని ఇక్కడి నుంచే ప్రచురించవచ్చు.",
                        fontFamily = MallannaFont,
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onPublishNewsClick,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "కొత్త వ్యంగ్య వార్తను రాయండి",
                            fontFamily = MallannaFont,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // Statutory Policy Pages under Indian Laws (భారతీయ చట్టాల నిబంధనలు)
        Text(
            text = "చట్టబద్ధ విధానాలు & పాలసీలు (Legal & Policies)",
            fontFamily = RamabhadraFont,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column {
                PolicyItem(
                    icon = Icons.Default.Warning,
                    title = "వ్యంగ్య ప్రకటన & ఉద్దేశం (Satire Disclaimer)",
                    subtitle = "కల్పిత హాస్యం మరియు భావప్రకటనా స్వేచ్ఛ వివరణ",
                    onClick = { onNavigateToPolicy("disclaimer") }
                )
                HorizontalDivider()
                PolicyItem(
                    icon = Icons.Default.SupportAgent,
                    title = "ఫిర్యాదుల పరిష్కారం (Grievance Redressal)",
                    subtitle = "భారతీయ IT Rules 2021 చట్టం ప్రకారం గ్రీవెన్స్ సెల్",
                    onClick = { onNavigateToPolicy("grievance") }
                )
                HorizontalDivider()
                PolicyItem(
                    icon = Icons.Default.PrivacyTip,
                    title = "గోప్యతా విధానం (Privacy Policy)",
                    subtitle = "డేటా రక్షణ మరియు DPDP Act 2023 నిబంధనలు",
                    onClick = { onNavigateToPolicy("privacy") }
                )
                HorizontalDivider()
                PolicyItem(
                    icon = Icons.Default.Description,
                    title = "నిబంధనలు & షరతులు (Terms of Service)",
                    subtitle = "పోర్టల్ వినియోగ నిబంధనలు మరియు బాధ్యతలు",
                    onClick = { onNavigateToPolicy("terms") }
                )
            }
        }
    }
}

@Composable
private fun PolicyItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = RamabhadraFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Text(
                text = subtitle,
                fontFamily = MallannaFont,
                fontSize = 13.sp,
                color = TextSecondary
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextMuted
        )
    }
}
