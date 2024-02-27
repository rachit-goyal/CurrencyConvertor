package com.learn.currencyconvertor.data.remote.retrofit

import com.learn.currencyconvertor.data.remote.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
created by Rachit on 2/21/2024.
 */
interface CurrencyAPI {

    @GET("/latest.json")
    suspend fun getCurrencyList(@Query("app_id") apiKey: String = Companion.apiKey)
            : Response<CurrencyResponse>

    companion object {
        const val apiKey = "f113cca145114e19b7b7833c5c0557a6"
    }
}


