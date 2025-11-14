package com.example.abbasali_task_robustrade.domain.repository

import com.example.abbasali_task_robustrade.domain.model.DataState
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
   suspend fun getNews(): Flow<DataState<List<NewsDataModel>>>
}