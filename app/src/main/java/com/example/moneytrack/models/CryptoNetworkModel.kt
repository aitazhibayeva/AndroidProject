package com.example.moneytrack.models


data class CryptoResponse(
    val data: List<CryptoNetworkModel>
)

data class CryptoNetworkModel(
    val id: String,
    val symbol: String,
    val name: String,
    val price_usd: String
)
