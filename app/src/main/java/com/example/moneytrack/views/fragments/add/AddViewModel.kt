package com.example.moneytrack.views.fragments.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytrack.utils.Database.AppDatabase
import com.example.moneytrack.utils.Database.Transaction
import com.notkamui.keval.Keval
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers

class AddViewModel(private val db: AppDatabase) : ViewModel() {

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    fun saveRecord(transaction: Transaction) {
        Log.e("TAG", "$transaction")
        viewModelScope.launch(Dispatchers.IO) {
            db.transactionDao().insertAll(transaction)
        }
    }

    fun calculateResult(expression: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if(expression.isNotEmpty()){
                Log.e("TAG", "expression: $expression")
                val total = Keval.eval(expression).toString()
                _result.postValue(total)
            }else{
                _result.postValue("0")
            }
        }
    }
}



class AddViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
