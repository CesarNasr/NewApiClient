package com.example.newapiclient.domain.usecase

import com.example.newapiclient.data.model.Article
import com.example.newapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    fun execute() : Flow<List<Article>>{
        return newsRepository.getSavedNews()
    }
}