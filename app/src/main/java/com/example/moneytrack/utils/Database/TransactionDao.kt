package com.example.moneytrack.utils.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions")
    fun getAll():LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id IN (:transactionIds)")
    fun loadAllByIds(transactionIds: IntArray): List<Transaction>

    @Insert
    fun insertAll(vararg transactions: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Update
    fun update(vararg transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE " +
            "type LIKE '%' || :query || '%' OR " +
            "paymentMethod LIKE '%' || :query || '%' OR " +
            "amount LIKE '%' || :query || '%' OR " +
            "category LIKE '%' || :query || '%'")
    fun searchTransactions(query: String): List<Transaction>

}