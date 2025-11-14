package com.example.abbasali_task_robustrade.presentation.ui.news


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import com.example.abbasali_task_robustrade.presentation.theme.AppColors
import com.example.abbasali_task_robustrade.presentation.theme.AppSpacing
import com.example.abbasali_task_robustrade.presentation.theme.AppTypography
import com.example.abbasali_task_robustrade.presentation.theme.DefaultSpacing
import com.example.abbasali_task_robustrade.presentation.theme.DefaultTypography
import com.example.abbasali_task_robustrade.presentation.theme.NewsApp

@Composable
fun NewsItem(
    newsModel: NewsDataModel,
    modifier: Modifier = Modifier,
    spacing: AppSpacing = DefaultSpacing

) {
    val typography: AppTypography = DefaultTypography
    Column(modifier = modifier.padding(spacing.large)) {
        Text(
            text = newsModel.sourceName,
            style = typography.bodyMedium,
            color = NewsApp.colors.primary
        )
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(newsModel.imageUrl)
                    .build(),
                contentDescription = newsModel.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(spacing.extraLarge * 3)
                    .clip(RoundedCornerShape(spacing.medium))
            )

            Spacer(modifier = Modifier.width(spacing.large))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {

                Text(
                    text = newsModel.title,
                    style = typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = newsModel.desc,
                    style = typography.captionMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(top = spacing.small, bottom = spacing.medium)
                )
                Text(
                    modifier = Modifier.padding(vertical = spacing.large),
                    text = newsModel.publishedDate,
                    style = typography.captionSmall,
                    color = Color.Gray
                )

            }
        }
    }
    HorizontalDivider(thickness = 1.dp, color = NewsApp.colors.dividerColor)

}


@Composable
@Preview(showBackground = true)
private fun HoldingItem_Preview() {
    NewsItem(
        NewsDataModel(
            id = "asdadadas3048430",
            title = "Geo Magnetic storm will rise in the coming years",
            imageUrl = "SampleUrl",
            sourceName = "Daniel Washington",
            sourceUrl = "SampleUrl",
            sourceCountry = "U.S.A",
            publishedDate = "25th Nov 2025 04:20pm",
            content = "Geo Magnetic storm will rise in the coming years,Geo Magnetic storm will rise in the coming years,Geo Magnetic storm will rise in the coming years,Geo Magnetic storm will rise in the coming years",
            desc = "Geo Magnetic storm will rise in the coming years,Geo Magnetic storm will rise in the coming years,Geo Magnetic storm will rise in the coming years,Geo Magnetic storm will rise in the coming years"
        )
    )
}
