package com.example.abbasali_task_robustrade.presentation.ui.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.abbasali_task_robustrade.R
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import com.example.abbasali_task_robustrade.presentation.theme.AppSpacing
import com.example.abbasali_task_robustrade.presentation.theme.AppTypography
import com.example.abbasali_task_robustrade.presentation.theme.DefaultSpacing
import com.example.abbasali_task_robustrade.presentation.theme.DefaultTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(news: NewsDataModel, spacing: AppSpacing = DefaultSpacing) {
    val typography: AppTypography = DefaultTypography
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = { Text(stringResource(R.string.details)) }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = spacing.large)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(vertical = spacing.large),
                text = news.title,
                style = typography.bodyBold
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = news.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(spacing.medium))
            )

            Text(
                text = news.sourceName,
                style = typography.bodyMedium,
                modifier = Modifier.padding(top=spacing.large)
            )
            Text(
                text = news.publishedDate,
                style = typography.captionSmall,
            )

            Text(
                text = news.desc,
                style = typography.bodyMedium,
                modifier = Modifier.padding(vertical = spacing.large)
            )

            Text(
                text = news.content,
                style = typography.captionMedium,
            )
        }
    }

}
