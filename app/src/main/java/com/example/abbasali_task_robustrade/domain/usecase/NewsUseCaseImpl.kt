package com.example.abbasali_task_robustrade.domain.usecase

import com.example.abbasali_task_robustrade.domain.model.DataState
import com.example.abbasali_task_robustrade.domain.repository.NewsRepository
import com.example.abbasali_task_robustrade.domain.toReadableDate
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class NewsUseCaseImpl @Inject constructor(private val newsRepository: NewsRepository) :
    NewsUseCase {

    override suspend fun getNewsData() = newsRepository.getNews().map { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    val modified = dataState.response.map {
                        it.copy(publishedDate = it.publishedDate.toReadableDate())
                    }
                    DataState.Success(modified)
                }
                else -> dataState
            }
        }
}