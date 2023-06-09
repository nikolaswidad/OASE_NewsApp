package com.nikolaswidad.oasesimpleapp.ui.screen.news

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import com.nikolaswidad.oasesimpleapp.R
import com.nikolaswidad.oasesimpleapp.data.local.entity.NewsEntity
import com.nikolaswidad.oasesimpleapp.ui.ViewModelFactory

class NewsScreenActivity : AppCompatActivity() {

    private lateinit var newsDetail: NewsEntity
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: NewsScreenViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsDetail = intent.getParcelableExtra<NewsEntity>(NEWS_DATA) as NewsEntity

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NewsScreen(
                        newsDetail,
                        viewModel
                    )
                }
            }
        }
    }

    companion object {
        const val NEWS_DATA = "data"
    }
}

@Composable
fun NewsScreen(
    news: NewsEntity,
    viewModel: NewsScreenViewModel
) {
    viewModel.setNewsData(news)
    val bookmarkStatus by viewModel.bookmarkStatus.observeAsState(false)
    NewsWebView(
        news.title,
        news.url.toString(),
        bookmarkStatus,
        updateBookmarkStatus = {
            viewModel.changeBookmark(news)
        }
    )
}

@Composable
private fun NewsWebView(
    title: String,
    url: String,
    bookmarkStatus: Boolean,
    updateBookmarkStatus: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {
                    IconButton(onClick = updateBookmarkStatus) {
                        Icon(
                            painter = if (bookmarkStatus) {
                                painterResource(R.drawable.ic_bookmarked)
                            } else {
                                painterResource(R.drawable.ic_bookmark)
                            },
                            contentDescription = stringResource(R.string.save_bookmark),
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->

        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            AndroidView(
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                    }
                },
                update = {
                    it.loadUrl(url)
                }
            )
        }
    }



    Column {
        AndroidView(factory = {
            WebView(it).apply {
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        })
    }
}


