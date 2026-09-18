package com.alfanewgen.galpika

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.alfanewgen.galpika.data.model.NewsArticle
import com.alfanewgen.galpika.data.repository.AuthRepository
import com.alfanewgen.galpika.data.repository.NewsRepository
import com.alfanewgen.galpika.theme.GalpikaTheme
import com.alfanewgen.galpika.ui.components.GalpikaBottomBar
import com.alfanewgen.galpika.ui.components.GalpikaDrawer
import com.alfanewgen.galpika.ui.components.GalpikaTopBar
import com.alfanewgen.galpika.ui.screens.detail.DetailScreen
import com.alfanewgen.galpika.ui.screens.home.HomeScreen
import com.alfanewgen.galpika.ui.screens.legal.PrivacyPolicyScreen
import com.alfanewgen.galpika.ui.screens.legal.SatireDisclaimerScreen
import com.alfanewgen.galpika.ui.screens.legal.TermsScreen
import com.alfanewgen.galpika.ui.screens.local.LocalScreen
import com.alfanewgen.galpika.ui.screens.news.NewsScreen
import com.alfanewgen.galpika.ui.screens.profile.GrievanceScreen
import com.alfanewgen.galpika.ui.screens.profile.ProfileScreen
import com.alfanewgen.galpika.ui.screens.profile.PublishNewsScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var newsRepository: NewsRepository

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GalpikaTheme {
                MainAppScreen(newsRepository, authRepository)
            }
        }
    }
}

@Composable
fun MainAppScreen(
    newsRepository: NewsRepository,
    authRepository: AuthRepository
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var currentTab by remember { mutableStateOf("home") } // "home", "local", "news", "profile"
    var currentSubScreen by remember { mutableStateOf<String?>(null) } // "detail", "publish", "grievance", "disclaimer", "privacy", "terms"
    var selectedArticle by remember { mutableStateOf<NewsArticle?>(null) }
    var selectedCategoryFilter by remember { mutableStateOf("అన్ని") }

    // Google Sign In Launcher
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                scope.launch {
                    val authResult = authRepository.signInWithCredential(credential)
                    if (authResult.isSuccess) {
                        Toast.makeText(context, "స్వాగతం, ${account.displayName}!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "లాగిన్ విఫలమైంది: ${authResult.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Google లాగిన్ ఎర్రర్: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun launchGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("998854593828-glinhaaj0joggr79fr4j9k6ubls97mau.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(context, gso)
        googleSignInLauncher.launch(client.signInIntent)
    }

    fun shareArticle(article: NewsArticle) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "😂 గల్పిక వ్యంగ్య వార్త:\n\n*${article.title}*\n\n${article.summary}\n\nమరిన్ని ఆసక్తికరమైన హాస్య & వ్యంగ్య కథనాల కోసం 'గల్పిక' యాప్‌ను డౌన్‌లోడ్ చేసుకోండి!"
            )
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(sendIntent, "వార్తను షేర్ చేయండి"))
    }

    fun shareApp() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "గల్పిక - స్వచ్ఛమైన తెలుగు వ్యంగ్య, హాస్య మరియు పేరడీ న్యూస్ అప్లికేషన్! ఇప్పుడే డౌన్‌లోడ్ చేసుకోండి: https://github.com/alfanews0861/Galpika"
            )
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(sendIntent, "గల్పిక యాప్ షేర్ చేయండి"))
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            GalpikaDrawer(
                onCategoryClick = { cat ->
                    selectedCategoryFilter = cat
                    currentTab = "news"
                    currentSubScreen = null
                },
                onNavigate = { screen ->
                    currentSubScreen = screen
                },
                onShareApp = { shareApp() },
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                if (currentSubScreen == null) {
                    GalpikaTopBar(
                        title = when (currentTab) {
                            "home" -> "గల్పిక"
                            "local" -> "స్థానిక సెటైర్లు"
                            "news" -> "వార్తా విభాగం"
                            "profile" -> "ప్రొఫైల్ & పాలసీలు"
                            else -> "గల్పిక"
                        },
                        onMenuClick = {
                            scope.launch { drawerState.open() }
                        },
                        onShareClick = {
                            shareApp()
                        }
                    )
                }
            },
            bottomBar = {
                if (currentSubScreen == null) {
                    GalpikaBottomBar(
                        currentRoute = currentTab,
                        onTabSelected = { tab ->
                            currentTab = tab
                            currentSubScreen = null
                        }
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentSubScreen) {
                    "detail" -> {
                        selectedArticle?.let { article ->
                            DetailScreen(
                                article = article,
                                onBack = { currentSubScreen = null },
                                onShare = { shareArticle(article) }
                            )
                        } ?: run {
                            currentSubScreen = null
                        }
                    }
                    "publish" -> {
                        PublishNewsScreen(
                            newsRepository = newsRepository,
                            onBack = { currentSubScreen = null },
                            onPublishedSuccess = {
                                currentSubScreen = null
                                currentTab = "home"
                            }
                        )
                    }
                    "grievance" -> {
                        GrievanceScreen(
                            newsRepository = newsRepository,
                            onBack = { currentSubScreen = null }
                        )
                    }
                    "disclaimer" -> {
                        SatireDisclaimerScreen(
                            onBack = { currentSubScreen = null }
                        )
                    }
                    "privacy" -> {
                        PrivacyPolicyScreen(
                            onBack = { currentSubScreen = null }
                        )
                    }
                    "terms" -> {
                        TermsScreen(
                            onBack = { currentSubScreen = null }
                        )
                    }
                    else -> {
                        when (currentTab) {
                            "home" -> {
                                HomeScreen(
                                    newsRepository = newsRepository,
                                    onArticleClick = { article ->
                                        selectedArticle = article
                                        currentSubScreen = "detail"
                                    },
                                    onShareArticle = { article ->
                                        shareArticle(article)
                                    }
                                )
                            }
                            "local" -> {
                                LocalScreen(
                                    newsRepository = newsRepository,
                                    onArticleClick = { article ->
                                        selectedArticle = article
                                        currentSubScreen = "detail"
                                    },
                                    onShareArticle = { article ->
                                        shareArticle(article)
                                    }
                                )
                            }
                            "news" -> {
                                NewsScreen(
                                    newsRepository = newsRepository,
                                    initialCategory = selectedCategoryFilter,
                                    onArticleClick = { article ->
                                        selectedArticle = article
                                        currentSubScreen = "detail"
                                    },
                                    onShareArticle = { article ->
                                        shareArticle(article)
                                    }
                                )
                            }
                            "profile" -> {
                                ProfileScreen(
                                    authRepository = authRepository,
                                    onGoogleSignInClick = { launchGoogleSignIn() },
                                    onPublishNewsClick = { currentSubScreen = "publish" },
                                    onNavigateToPolicy = { policyRoute ->
                                        currentSubScreen = policyRoute
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
