package com.example.abbasali_task_robustrade

import com.example.abbasali_task_robustrade.domain.model.DataState
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import com.example.abbasali_task_robustrade.domain.repository.NewsRepository
import com.example.abbasali_task_robustrade.domain.toReadableDate
import com.example.abbasali_task_robustrade.domain.usecase.NewsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsUseCaseImplTest {

    private val newsRepository = mockk<NewsRepository>()
    private lateinit var newsUseCase: NewsUseCaseImpl

    private val testDispatcher = StandardTestDispatcher()

    val sampleNewsList = listOf(
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
        Dispatchers.setMain(testDispatcher)
        newsUseCase = NewsUseCaseImpl(newsRepository)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getNewsData should convert publishedDate and return Modified Success`() = runTest {
        val expectedReadableDate = "13th nov 2025 2:10 am"

        mockkStatic("com.example.abbasali_task_robustrade.domain.KtxKt")
        every { any<String>().toReadableDate() } returns expectedReadableDate

        coEvery { newsRepository.getNews() } returns flow {
            emit(DataState.Success(sampleNewsList))
        }

        val resultFlow = newsUseCase.getNewsData()
        val result = resultFlow.first()

        assert(result is DataState.Success)
        val data = (result as DataState.Success).response

        assert(data[0].publishedDate == expectedReadableDate)
        coVerify { newsRepository.getNews() }
    }

    @Test
    fun `getNewsData should pass through non-success states`() = runTest {
        val errorState = DataState.Error("Something went wrong")
        coEvery { newsRepository.getNews() } returns flow {
            emit(errorState)
        }
        val result = newsUseCase.getNewsData().first()
        assert(result is DataState.Error)
        assert((result as DataState.Error).errorMessage == "Something went wrong")
        coVerify { newsRepository.getNews() }
    }
}
