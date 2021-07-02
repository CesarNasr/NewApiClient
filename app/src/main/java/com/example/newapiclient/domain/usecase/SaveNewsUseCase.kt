package com.example.newapiclient.domain.usecase

import com.example.newapiclient.data.model.Article
import com.example.newapiclient.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}