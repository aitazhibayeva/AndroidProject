package com.example.moneytrack.views.fragments.news.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.moneytrack.network.CurrencyAPI
import kotlinx.coroutines.Dispatchers

class CurrencyViewModel(private val api: CurrencyAPI) : ViewModel() {
    fun getAllCurrency(to: String) = liveData(Dispatchers.IO) {
        val response = api.getAllCurrency(to)
        emit(response)
    }
}

class CurrencyViewModelFactory(private val api: CurrencyAPI) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
