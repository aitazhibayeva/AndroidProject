package com.example.moneytrack.views.fragments.news.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.moneytrack.network.CryptoAPI
import kotlinx.coroutines.Dispatchers

class CryptoViewModel(private val api: CryptoAPI) : ViewModel() {
    fun getAllCrypto() = liveData(Dispatchers.IO) {
        val response = api.getAllCrypto()
        emit(response)
    }
}

class CryptoViewModelFactory(private val api: CryptoAPI) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CryptoViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
