package com.example.newapiclient.domain.usecase

import com.example.newapiclient.data.model.Article
import com.example.newapiclient.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}