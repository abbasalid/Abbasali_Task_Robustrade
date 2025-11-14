package com.example.abbasali_task_robustrade.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abbasali_task_robustrade.domain.model.DataState
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import com.example.abbasali_task_robustrade.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    private var originalNewsList: List<NewsDataModel> = emptyList()

    init {
        getGoogleNews()
        observeSearchQuery()
    }

    private fun getGoogleNews() {
        viewModelScope.launch(dispatcher) {
            newsUseCase.getNewsData().collect { state ->
                try {
                    when (state) {
                        is DataState.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true,
                                    error = null
                                )
                            }
                        }

                        is DataState.Success -> {
                            originalNewsList = state.response
                            _uiState.update {
                                it.copy(
                                    newsList = state.response,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }

                        is DataState.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = state.errorMessage
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Failed to load news"
                        )
                    }
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch(dispatcher) {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    filterNews(query)
                }
        }
    }

    private fun filterNews(query: String) {
        if (query.isBlank()) {
            _uiState.update {
                it.copy(newsList = originalNewsList, isEmpty = false)
            }
            return
        }

        val filteredList = originalNewsList.filter { news ->
            news.title.contains(query, ignoreCase = true) ||
                    news.desc.contains(query, ignoreCase = true) ||
                    news.content.contains(query, ignoreCase = true) ||
                    news.sourceName.contains(query, ignoreCase = true)
        }

        _uiState.update {
            it.copy(
                newsList = filteredList,
                isEmpty = filteredList.isEmpty()
            )
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class UIState(
    val newsList: List<NewsDataModel> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isEmpty: Boolean = false

)