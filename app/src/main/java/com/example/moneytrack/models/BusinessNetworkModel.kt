package com.example.moneytrack.models

data class BusinessResponse(
    val results: List<BusinessNetworkModel>
)


data class BusinessNetworkModel(
    val title: String,
    val link: String,
    val description: String,
    val image_url: String
)
