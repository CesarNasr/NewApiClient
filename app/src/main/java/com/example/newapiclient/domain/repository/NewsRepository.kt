package com.example.newapiclient.domain.repository

import com.example.newapiclient.data.model.APIResponse
import com.example.newapiclient.data.model.Article
import com.example.newapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse>
    suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Resource<APIResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
}