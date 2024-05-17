package com.example.moneytrack.network

import com.example.moneytrack.models.BusinessResponse
import retrofit2.http.GET

interface BusinessAPI {


    @GET("news?apikey=pub_4427989bed7a53ae41c0949135f858ef31c9e&category=business&language=en")
    suspend fun getBusinessInfo(): BusinessResponse
}