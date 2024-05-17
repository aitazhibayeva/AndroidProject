package com.example.moneytrack.views.fragments.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneytrack.utils.Database.AppDatabase
import com.github.mikephil.charting.data.PieEntry
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap


class TransactionViewModel(private val db: AppDatabase) : ViewModel() {

    private val _pieEntries = MutableLiveData<List<PieEntry>>()
    val pieEntries: LiveData<List<PieEntry>> = db.transactionDao().getAll().switchMap {data->
        val groupedTransactions = data.groupBy { it.category }
        val entries = groupedTransactions.map { (category, transactions) ->
            var totalAmount = 0f
            transactions.forEach { totalAmount += it.amount }
            PieEntry(totalAmount, category)
        }
        MutableLiveData(entries)
    }



}




class TransactionViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

