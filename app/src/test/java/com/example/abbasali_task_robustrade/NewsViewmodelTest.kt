package com.example.abbasali_task_robustrade

import com.example.abbasali_task_robustrade.domain.model.DataState
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import com.example.abbasali_task_robustrade.domain.usecase.NewsUseCase
import com.example.abbasali_task_robustrade.presentation.vm.NewsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewmodelTest {

    private lateinit var newsUseCase: NewsUseCase
    private lateinit var viewModel: NewsViewModel

    val testData = listOf(
        NewsDataModel(
            id = "1",
            title = "AI Breakthrough: New Model Achieves Human-Level Reasoning",
            imageUrl = "https://example.com/images/ai_breakthrough.jpg",
            sourceName = "TechCrunch",
            sourceUrl = "https://techcrunch.com/ai-breakthrough",
            sourceCountry = "USA",
            publishedDate = "2025-11-13T02:10:52Z",
            content = "Researchers have announced a major milestone in AI development...",
            desc = "A new AI model reaches human-level reasoning in benchmark tests."
        )
    )


    @Before
    fun setup() {
        newsUseCase = mockk()
        Dispatchers.setMain(StandardTestDispatcher())
    }
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState is updated to Loading`() = runTest {
        coEvery { newsUseCase.getNewsData() } returns flowOf(DataState.Loading)

        val testDispatcher = StandardTestDispatcher(testScheduler)
        viewModel = NewsViewModel(newsUseCase, testDispatcher)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state.isLoading)
        assert(state.error == null)
    }

    @Test
    fun `uiState is updated to Success`() = runTest {

        coEvery { newsUseCase.getNewsData() } returns flowOf(DataState.Success(testData))

        val testDispatcher = StandardTestDispatcher(testScheduler)
        viewModel = NewsViewModel(newsUseCase, testDispatcher)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state.newsList == testData)
        assert(state.error == null)
    }

    @Test
    fun `uiState is updated to Error`() = runTest {
        coEvery { newsUseCase.getNewsData() } returns flowOf(DataState.Error("Network error"))

        val testDispatcher = StandardTestDispatcher(testScheduler)
        viewModel = NewsViewModel(newsUseCase, testDispatcher)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(!state.isLoading)
        assert(state.error == "Network error")
    }

    @Test
    fun `clearError resets error`() = runTest {
        coEvery { newsUseCase.getNewsData() } returns flowOf(DataState.Error("Network error"))
        val testDispatcher = StandardTestDispatcher(testScheduler)
        viewModel = NewsViewModel(newsUseCase, testDispatcher)
        advanceUntilIdle()
        viewModel.clearError()
        val state = viewModel.uiState.value
        assert(state.error == null)
    }
}
