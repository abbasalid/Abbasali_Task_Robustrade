package com.example.abbasali_task_robustrade.domain.usecase

import com.example.abbasali_task_robustrade.domain.model.DataState
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    suspend fun getNewsData(): Flow<DataState<List<NewsDataModel>>>

}