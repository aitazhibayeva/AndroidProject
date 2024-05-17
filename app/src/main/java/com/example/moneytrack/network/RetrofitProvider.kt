package com.example.moneytrack.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    fun provideRetrofitForCurrency(): Retrofit{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.freecurrencyapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    fun provideRetrofitForBusiness(): Retrofit{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsdata.io/api/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    fun provideRetrofitForCrypto(): Retrofit{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coinlore.net/api/tickers/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }


    fun provideCurrencyApi(retrofit: Retrofit): CurrencyAPI{
        return retrofit.create(CurrencyAPI::class.java)
    }

    fun provideCryptoApi(retrofit: Retrofit): CryptoAPI{
        return retrofit.create(CryptoAPI::class.java)
    }

    fun provideBusinessApi(retrofit: Retrofit): BusinessAPI{
        return retrofit.create(BusinessAPI::class.java)
    }
}