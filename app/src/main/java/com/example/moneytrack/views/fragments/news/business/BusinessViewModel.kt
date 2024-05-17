package com.example.moneytrack.views.fragments.news.business


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.moneytrack.network.BusinessAPI
import kotlinx.coroutines.Dispatchers

class BusinessViewModel(private val api: BusinessAPI) : ViewModel() {
    fun getBusinessInfo() = liveData(Dispatchers.IO) {
        val response = api.getBusinessInfo()
        emit(response)
    }
}

class BusinessViewModelFactory(private val api: BusinessAPI) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusinessViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusinessViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
