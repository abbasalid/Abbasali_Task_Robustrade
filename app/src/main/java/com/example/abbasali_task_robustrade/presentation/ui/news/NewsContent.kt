package com.example.abbasali_task_robustrade.presentation.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import com.example.abbasali_task_robustrade.presentation.ui.ErrorMessage
import com.example.abbasali_task_robustrade.presentation.vm.UIState

@Composable
fun NewsContent(
    uiState: UIState,
    callback: NewsScreenCallbacks,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        uiState.error?.let { error ->
            ErrorMessage(
                error = error,
                onDismiss = {
                    callback.onErrorDismissed.invoke()
                },
            )
        }
        LazyColumn {
            items(
                items = uiState.newsList,
                key = { it.id }
            ) { news ->
                NewsItem(newsModel = news, modifier = Modifier.clickable {
                    callback.onItemClick(news)
                })
            }
        }
    }
}

@Composable
@Preview
private fun NewsContent_Preview() {
    NewsContent(
        uiState = UIState(),
        callback = object : NewsScreenCallbacks {
            override val onBackPressed: () -> Unit
                get() = {}
            override val onErrorDismissed: () -> Unit
                get() = { }
            override val onSearch: (String) -> Unit
                get() = { }
            override val onItemClick: (NewsDataModel) -> Unit
                get() = { }

        }
    )
}