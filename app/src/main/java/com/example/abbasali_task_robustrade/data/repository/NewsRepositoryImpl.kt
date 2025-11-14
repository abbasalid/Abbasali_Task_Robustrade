package com.example.abbasali_task_robustrade.data.repository

import com.example.abbasali_task_robustrade.data.remote.service.NewsService
import com.example.abbasali_task_robustrade.domain.model.DataState
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import com.example.abbasali_task_robustrade.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException

import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(private val newsService: NewsService) :
    NewsRepository {

    override suspend fun getNews(): Flow<DataState<List<NewsDataModel>>> {
        return safeApiCall {
            newsService.getNews()
        }.map { result ->
                when (result) {
                    is DataState.Success -> {
                        val news = result.response.articles.map { response ->
                            NewsDataModel(
                                id = response.id,
                                title = response.title,
                                imageUrl = response.image,
                                publishedDate = response.publishedAt,
                                sourceName = response.source.name,
                                sourceUrl = response.source.url,
                                sourceCountry = response.source.country,
                                desc = response.description,
                                content = response.content
                            )
                        }
                        DataState.Success(news)
                    }

                    is DataState.Error -> DataState.Error(result.errorMessage)
                    is DataState.Loading -> DataState.Loading
                }
            }
    }

    fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Flow<DataState<T>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(DataState.Success(it))
                } ?: emit(DataState.Error("Response body is null"))
            } else {
                emit(DataState.Error("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    emit(DataState.Error("Network or I/O error occurred"))
                }
                else -> {
                    emit(DataState.Error(e.message ?: "Something went wrong"))
                }
            }

        }
    }
}