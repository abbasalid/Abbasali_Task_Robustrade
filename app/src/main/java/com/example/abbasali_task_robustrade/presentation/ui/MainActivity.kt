package com.example.abbasali_task_robustrade.presentation.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.abbasali_task_robustrade.domain.model.NewsDataModel
import com.example.abbasali_task_robustrade.presentation.theme.NewsAppTheme
import com.example.abbasali_task_robustrade.presentation.ui.news.NewsDetailScreen
import com.example.abbasali_task_robustrade.presentation.ui.news.NewsScreen
import com.example.abbasali_task_robustrade.presentation.ui.news.NewsScreenCallbacks
import com.example.abbasali_task_robustrade.presentation.vm.NewsViewModel
import com.example.abbasali_task_robustrade.presentation.vm.UIState
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val state by viewModel.uiState.collectAsStateWithLifecycle()

            NewsAppTheme {
                NavComposable(state)
            }
        }
    }

    @Composable
    private fun NavComposable(state: UIState) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Routes.NewsHomePage
        ) {
            //News Home Page Screen
            composable(Routes.NewsHomePage) {
                NewsScreen(
                    state = state,
                    callback = object : NewsScreenCallbacks {
                        override val onBackPressed: () -> Unit
                            get() = { this.onBackPressed() }
                        override val onErrorDismissed: () -> Unit
                            get() = { viewModel.clearError() }
                        override val onSearch: (String) -> Unit
                            get() = { viewModel.onQueryChange(it) }
                        override val onItemClick: (NewsDataModel) -> Unit
                            get() = { news ->
                                val json = Uri.encode(Gson().toJson(news))
                                navController.navigate("news_detail/$json")
                            }
                    }
                )
            }

            //Details Screen
            composable(
                route = Routes.NewsDetail,
                arguments = listOf(navArgument("newsItem") { type = NavType.StringType })
            ) { backStackEntry ->
                val json = backStackEntry.arguments?.getString("newsItem")
                val newsItem = Gson().fromJson(json, NewsDataModel::class.java)
                NewsDetailScreen(news = newsItem)
            }
        }
    }
}