package com.example.moneytrack.network

import com.example.moneytrack.models.CurrencyNetworkModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyAPI {


    @GET("latest?apikey=fca_live_S4DETGWTrWeaFOkkEdXjrH4lxs6Geg7FzXqz8upQ&base_currency=USD")
    suspend fun getAllCurrency(@Query("currencies") currencies: String): CurrencyNetworkModel
}