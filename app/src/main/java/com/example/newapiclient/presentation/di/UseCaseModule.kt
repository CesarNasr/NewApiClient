package com.example.newapiclient.presentation.di

import com.example.newapiclient.domain.repository.NewsRepository
import com.example.newapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newapiclient.domain.usecase.GetSearchedNewUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetNewsHeadlinesUseCase(newsRepository: NewsRepository) : GetNewsHeadlinesUseCase{
        return GetNewsHeadlinesUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideGetSearchedNewsUseCase(newsRepository: NewsRepository) : GetSearchedNewUseCase{
        return GetSearchedNewUseCase(newsRepository)
    }


}