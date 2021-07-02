package com.example.newapiclient.data.repository.datasourceimpl

import com.example.newapiclient.data.api.NewsAPIService
import com.example.newapiclient.data.model.APIResponse
import com.example.newapiclient.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(private val newsApiService: NewsAPIService) : NewsRemoteDataSource {

    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {
        return newsApiService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse> {
        return newsApiService.getSearchedTopHeadlines(country, searchQuery, page)
    }

}