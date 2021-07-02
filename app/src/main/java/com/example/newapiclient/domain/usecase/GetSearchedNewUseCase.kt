package com.example.newapiclient.domain.usecase

import com.example.newapiclient.data.model.APIResponse
import com.example.newapiclient.data.util.Resource
import com.example.newapiclient.domain.repository.NewsRepository

class GetSearchedNewUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, searchQuery: String, page: Int): Resource<APIResponse> {
        return newsRepository.getSearchedNews(country, searchQuery, page)
    }
}