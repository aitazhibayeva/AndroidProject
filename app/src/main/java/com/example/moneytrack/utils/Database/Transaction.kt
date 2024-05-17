package com.example.moneytrack.utils.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // expense or income
    val paymentMethod: String, // card or cash
    val date: String,
    val amount: Float,
    val category: String
)