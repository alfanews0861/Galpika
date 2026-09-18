package com.alfanewgen.galpika.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfanewgen.galpika.data.model.NewsArticle
import com.alfanewgen.galpika.data.repository.NewsRepository
import com.alfanewgen.galpika.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishNewsScreen(
    newsRepository: NewsRepository,
    onBack: () -> Unit,
    onPublishedSuccess: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("https://images.unsplash.com/photo-1585829365295-ab7cd400c167?auto=format&fit=crop&w=800&q=80") }

    val categories = listOf("రాజకీయ వ్యంగ్యం", "సినిమా & వినోదం", "వైరల్ & ట్రెండింగ్", "క్రీడా సెటైర్లు", "కార్టూన్లు & మీమ్స్", "సమాజం & వింతలు")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var categoryExpanded by remember { mutableStateOf(false) }

    val districts = listOf("ఆంధ్రప్రదేశ్ & తెలంగాణ", "హైదరాబాద్", "విశాఖపట్నం", "విజయవాడ", "తిరుపతి", "వరంగల్", "గుంటూరు", "కర్నూలు")
    var selectedDistrict by remember { mutableStateOf(districts[0]) }
    var districtExpanded by remember { mutableStateOf(false) }

    var isSubmitting by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "వార్త రాయండి / ప్రచురించండి",
                        fontFamily = RamabhadraFont,
                        fontSize = 19.sp,
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Title Input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("వార్త శీర్షిక (Headline)", fontFamily = MallannaFont) },
                placeholder = { Text("ఆకర్షణీయమైన వ్యంగ్య శీర్షిక...", fontFamily = MallannaFont) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.titleMedium,
                shape = RoundedCornerShape(10.dp)
            )

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("విభాగం (Category)", fontFamily = MallannaFont) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(10.dp)
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat, fontFamily = MallannaFont) },
                            onClick = {
                                selectedCategory = cat
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            // District Dropdown
            ExposedDropdownMenuBox(
                expanded = districtExpanded,
                onExpandedChange = { districtExpanded = !districtExpanded }
            ) {
                OutlinedTextField(
                    value = selectedDistrict,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("ప్రాంతం / జిల్లా (District)", fontFamily = MallannaFont) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = districtExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(10.dp)
                )
                ExposedDropdownMenu(
                    expanded = districtExpanded,
                    onDismissRequest = { districtExpanded = false }
                ) {
                    districts.forEach { dist ->
                        DropdownMenuItem(
                            text = { Text(dist, fontFamily = MallannaFont) },
                            onClick = {
                                selectedDistrict = dist
                                districtExpanded = false
                            }
                        )
                    }
                }
            }

            // Image URL
            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("ఫోటో లింక్ (Image URL)", fontFamily = MallannaFont) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            // Summary
            OutlinedTextField(
                value = summary,
                onValueChange = { summary = it },
                label = { Text("చిన్న సారాంశం (Summary)", fontFamily = MallannaFont) },
                placeholder = { Text("2-3 వాక్యాలలో కథనం సారాంశం...", fontFamily = MallannaFont) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 3,
                shape = RoundedCornerShape(10.dp)
            )

            // Full Content
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("పూర్తి కథనం వివరాలు (Full Article Content)", fontFamily = MallannaFont) },
                placeholder = { Text("పూర్తి వ్యంగ్య కథనాన్ని ఇక్కడ రాయండి...", fontFamily = MallannaFont) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 6,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Submit Button
            Button(
                onClick = {
                    if (title.isBlank() || content.isBlank()) {
                        Toast.makeText(context, "శీర్షిక మరియు కథనం వివరాలు తప్పనిసరి!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    isSubmitting = true
                    scope.launch {
                        val article = NewsArticle(
                            title = title,
                            content = content,
                            summary = summary.ifBlank { content.take(120) + "..." },
                            category = selectedCategory,
                            district = selectedDistrict,
                            imageUrl = imageUrl
                        )
                        val res = newsRepository.publishNews(article)
                        isSubmitting = false
                        if (res.isSuccess) {
                            Toast.makeText(context, "వార్త విజయవంతంగా ప్రచురించబడింది!", Toast.LENGTH_LONG).show()
                            onPublishedSuccess()
                        } else {
                            Toast.makeText(context, "ఎర్రర్: ${res.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(10.dp)
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp))
                } else {
                    Icon(imageVector = Icons.Default.Publish, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "వార్తను ప్రచురించండి (Publish News)",
                        fontFamily = RamabhadraFont,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
