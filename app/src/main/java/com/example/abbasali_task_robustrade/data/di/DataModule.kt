package com.example.abbasali_task_robustrade.data.di

import com.example.abbasali_task_robustrade.data.repository.NewsRepositoryImpl
import com.example.abbasali_task_robustrade.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

}