package com.example.quotationapp.fragment.homeFragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotationapp.json.Quotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _quotesList = MutableLiveData<List<Quotes>>()
    val quotesList: LiveData<List<Quotes>> get() = _quotesList

    fun loadQuotes() {
        viewModelScope.launch {
            _quotesList.value = repository.getQuotesFromAssets()
        }
    }
}

