package com.example.abbasali_task_robustrade

import com.example.abbasali_task_robustrade.data.remote.model.Article
import com.example.abbasali_task_robustrade.data.remote.model.ResponseModel
import com.example.abbasali_task_robustrade.data.remote.model.Source
import com.example.abbasali_task_robustrade.data.remote.service.NewsService
import com.example.abbasali_task_robustrade.data.repository.NewsRepositoryImpl
import com.example.abbasali_task_robustrade.domain.model.DataState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryImplTest {

    private lateinit var repository: NewsRepositoryImpl
    private lateinit var newsService: NewsService

    private val testDataSuccess = ResponseModel(
        articles = listOf(
            Article(
                content = "Google has announced new AI features in its latest developer conference...",
                description = "Google introduces powerful AI tools to improve productivity.",
                id = "1",
                image = "https://picsum.photos/400/200",
                lang = "en",
                publishedAt = "2025-01-14T10:00:00Z",
                source = Source(
                    country = "US",
                    id = "google-news",
                    name = "Google News",
                    url = "https://news.google.com"
                ),
                title = "Google launches new AI features",
                url = "https://news.google.com/articles/ai"
            ),
            Article(
                content = "Apple reveals its next generation M4 chip with improved performance...",
                description = "Apple M4 chip promises groundbreaking speed and efficiency.",
                id = "2",
                image = "https://picsum.photos/400/201",
                lang = "en",
                publishedAt = "2025-01-14T12:00:00Z",
                source = Source(
                    country = "US",
                    id = "apple-news",
                    name = "Apple Newsroom",
                    url = "https://www.apple.com/newsroom"
                ),
                title = "Apple unveils M4 chip",
                url = "https://apple.com/newsroom/m4-chip"
            ),
        )
    )
    private val testDataError: Response<ResponseModel> = Response.error(
        500,
        "Internal Server Error".toResponseBody("application/json".toMediaTypeOrNull())
    )


    @Before
    fun setup() {
        newsService = mockk()
        repository = NewsRepositoryImpl(newsService)
    }

    @Test
    fun `getNews is success and returns mapped data with DataState Success`() = runTest {
        val mockResponse = testDataSuccess
        coEvery { newsService.getNews() } returns Response.success(mockResponse)

        val result = repository.getNews().toList()

        assert(result[0] is DataState.Loading)
        assert(result[1] is DataState.Success)

        val data = (result[1] as DataState.Success).response
        assert(data.size == 2)
        assert(data[0].id == "1")
        assert(data[0].title.contains("Google"))
    }

    @Test
    fun `getNews has api error returns DataState Error`() = runTest {

        coEvery { newsService.getNews() } returns testDataError

        val result = repository.getNews().toList()

        assert(result[0] is DataState.Loading)
        assert(result[1] is DataState.Error)
        assert((result[1] as DataState.Error).errorMessage.contains("500"))
    }

    @Test
    fun `getNews with null body returns DataState Error`() = runTest {
        coEvery {newsService.getNews() } returns Response.success(null)

        val result = repository.getNews().toList()

        assert(result[0] is DataState.Loading)
        assert(result[1] is DataState.Error)
        assert((result[1] as DataState.Error).errorMessage == "Response body is null")
    }

    @Test
    fun `getNews - ioexception thrown returns DataState Error`() = runTest {

        coEvery { newsService.getNews() } throws IOException("Network or I/O error occurred")
        val result = repository.getNews().toList()

        assert(result[0] is DataState.Loading)
        assert(result[1] is DataState.Error)
        assert((result[1] as DataState.Error).errorMessage == "Network or I/O error occurred")
    }
}
