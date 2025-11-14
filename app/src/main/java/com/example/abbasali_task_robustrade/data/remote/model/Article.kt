package com.example.abbasali_task_robustrade.data.remote.model

data class Article(
    val content: String,
    val description: String,
    val id: String,
    val image: String,
    val lang: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String
)