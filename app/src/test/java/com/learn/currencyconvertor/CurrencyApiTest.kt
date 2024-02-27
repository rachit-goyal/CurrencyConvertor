package com.learn.currencyconvertor

import com.learn.currencyconvertor.data.mapper.toCurrencyRate
import com.learn.currencyconvertor.data.remote.models.CurrencyRate
import com.learn.currencyconvertor.data.remote.retrofit.CurrencyAPI
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
created by Rachit on 2/23/2024.
 */
class CurrencyApiTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var currencyAPI: CurrencyAPI

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        currencyAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CurrencyAPI::class.java)
    }

    @Test
    fun testGetProducts() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = currencyAPI.getCurrencyList()
        mockWebServer.takeRequest()

        Assert.assertEquals(null,response.body()?.disclaimer)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}