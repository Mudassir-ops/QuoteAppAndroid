package com.example.quotationapp.fragment.quotesNumberFragment

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import jakarta.inject.Inject

class QuotesRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val _quotesNumber = MutableLiveData(getQuotesNumber())
    val quotesNumber: LiveData<Int> get() = _quotesNumber

    fun getQuotesNumber(): Int {
        return sharedPreferences.getInt("quotes_number", 6)
    }

    fun setQuotesNumber(value: Int) {
        sharedPreferences.edit().putInt("quotes_number", value).apply()
        _quotesNumber.value = value
    }
}