package com.example.moneytrack.network

import com.example.moneytrack.models.CryptoResponse
import retrofit2.http.GET

interface CryptoAPI {

    @GET("?start=100&limit=100")
    suspend fun getAllCrypto(): CryptoResponse
}