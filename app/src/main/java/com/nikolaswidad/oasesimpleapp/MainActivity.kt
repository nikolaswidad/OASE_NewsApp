package com.nikolaswidad.oasesimpleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikolaswidad.oasesimpleapp.ui.NewsViewModel
import com.nikolaswidad.oasesimpleapp.ui.ViewModelFactory
import com.nikolaswidad.oasesimpleapp.ui.components.NewsItemCard
import com.nikolaswidad.oasesimpleapp.ui.theme.OASESimpleAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vmf = ViewModelFactory.getInstance(applicationContext)
        val newsViewModel : NewsViewModel by viewModels { vmf }

        setContent {
            OASESimpleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NewsApp(newsViewModel)
                }
            }
        }
    }
}

@Composable
fun SectionTitle() {
    Text(
        text = "OASE",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    )
}

@Composable
fun NewsApp(
    newsViewModel: NewsViewModel
) {
//    val newsList by newsViewModel.newsList.collectAsState()
//    val newsList by newsViewModel.getNews().observe()
    val newsList by newsViewModel.newsList.collectAsState()
    val context = LocalContext.current

    // State untuk menyimpan teks pencarian
    var searchQuery by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        SectionTitle()
                    },
                )
            },
            content = {
                Column {
                    // Kolom pencarian
                    TextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        value = searchQuery,
                        onValueChange = { query ->
                            searchQuery = query
                            // Lakukan pencarian berdasarkan query
                            // atau panggil fungsi pencarian ke repository atau ViewModel
                        },
                        label = {
                            Text("Search")
                        }
                    )
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        val listState = rememberLazyListState()

                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .padding(12.dp),
                        ) {
                            // foreach NewsItem
                            items(newsList){
                                if(it.url != null) {
                                    NewsItemCard(
                                        title = it.title,
                                        image = it.urlToImage,
                                        url = it.url,
                                        source = "",
                                        publishedAt = it.publishedAt,
                                        credibilityScore = 50,
                                        sentiment = "positive",
                                        summary = "as",
                                        bookmarked = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    val context = LocalContext.current
    val newsViewModel: NewsViewModel = viewModel(factory = ViewModelFactory.getInstance(context))
    OASESimpleAppTheme {
        NewsApp(newsViewModel)
    }
}
