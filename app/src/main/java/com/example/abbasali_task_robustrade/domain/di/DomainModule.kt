package com.example.abbasali_task_robustrade.domain.di

import com.example.abbasali_task_robustrade.domain.usecase.NewsUseCase
import com.example.abbasali_task_robustrade.domain.usecase.NewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindNewsUseCase(
        NewsUseCaseImpl: NewsUseCaseImpl
    ): NewsUseCase

}