package com.example.abbasali_task_robustrade.presentation.ui.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.abbasali_task_robustrade.presentation.vm.UIState
import androidx.compose.ui.unit.dp
import com.example.abbasali_task_robustrade.R
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import com.example.abbasali_task_robustrade.presentation.theme.DefaultSpacing
import com.example.abbasali_task_robustrade.presentation.theme.DefaultTypography
import com.example.abbasali_task_robustrade.presentation.theme.NewsApp

import com.example.abbasali_task_robustrade.presentation.ui.search.SearchView

interface NewsScreenCallbacks {
    val onBackPressed: () -> Unit
    val onErrorDismissed: () -> Unit
    val onSearch: (query: String) -> Unit

    val onItemClick: (newsItem: NewsDataModel) -> Unit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    state: UIState,
    callback: NewsScreenCallbacks
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = { Text(stringResource(R.string.news)) }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f))
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            SearchView(
                modifier = Modifier.fillMaxWidth(),
                onSearchTriggered = { query ->
                    callback.onSearch(query)
                },
            )

            if (state.isLoading.not() && state.newsList.isEmpty() && state.isEmpty) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.wrapContentSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(55.dp),
                            imageVector = Icons.Default.Search,
                            contentDescription = "No search results icon",
                            tint = NewsApp.colors.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No data found.",
                            style = DefaultTypography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            if (state.newsList.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    NewsContent(
                        uiState = state,
                        callback = callback,
                        modifier = Modifier.padding(bottom = DefaultSpacing.large)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NewsScreen_Preview() {
    NewsScreen(state = UIState(), callback = object : NewsScreenCallbacks {
        override val onBackPressed: () -> Unit
            get() = {}
        override val onErrorDismissed: () -> Unit
            get() = { }
        override val onSearch: (String) -> Unit
            get() = { }
        override val onItemClick: (NewsDataModel) -> Unit
            get() = { }
    })
}