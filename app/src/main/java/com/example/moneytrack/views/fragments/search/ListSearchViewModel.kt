package com.example.moneytrack.views.fragments.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moneytrack.utils.Database.AppDatabase
import com.example.moneytrack.utils.Database.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(private val db: AppDatabase) : ViewModel() {

    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    init {
        getInitialData()
    }

    fun searchTransactions(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isNotEmpty()) {
                val searchResults = db.transactionDao().searchTransactions(query)
                Log.e("ListViewModel", "search: $searchResults")
                _transactions.postValue(searchResults)
            } else {
               getInitialData()
            }
        }
    }

    private fun getInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            val initialData = db.transactionDao().getAll()
            withContext(Dispatchers.Main){
                initialData.observeForever {
                    _transactions.postValue(it)
                }
            }
        }


    }
}

class ListViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
