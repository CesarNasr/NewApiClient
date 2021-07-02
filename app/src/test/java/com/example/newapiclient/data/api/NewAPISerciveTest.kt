package com.example.newapiclient.data.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewAPISerciveTest {
    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }


    private fun enqueueWebResponse(filename: String) {
        val inputSteam = javaClass.classLoader!!.getResourceAsStream(filename)
        val source = inputSteam.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }


    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueWebResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=c0f82999e4e04da68ccf37aa2c24a1ef")
        }
    }


    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueWebResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articles = responseBody!!.articles
            assertThat(articles.size).isEqualTo(20)
        }
    }


    @Test
    fun getTopHeadlines_receivedResponse_correctContent() {
        runBlocking {
            enqueueWebResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val firstArticle = responseBody!!.articles[0]

            assertThat(firstArticle.author).isEqualTo("Associated Press")
            assertThat(firstArticle.publishedAt).isEqualTo("2021-06-23T11:49:15Z")
            assertThat(firstArticle.url).isEqualTo("https://www.foxnews.com/world/russian-ship-fires-shots-uk-warship-black-sea")

        }
    }


    @After
    fun tearDown() {
        server.shutdown()
    }


}