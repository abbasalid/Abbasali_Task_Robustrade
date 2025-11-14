package com.example.abbasali_task_robustrade.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class NewsDataModel(
    val id : String,
    val title: String,
    val imageUrl: String,
    val sourceName: String,
    val sourceUrl: String,
    val sourceCountry: String,
    val publishedDate: String,
    val content: String,
    val desc: String
)