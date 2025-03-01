package com.example.quotationapp.fragment.categoriesFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotationapp.json.QuotesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val ioDispatcher = IO
    private val _quotesData = MutableStateFlow<QuotesData?>(null)
    val quotesData: StateFlow<QuotesData?> get() = _quotesData

    init {
        fetchQuotes(ioDispatcher)
    }

    private fun fetchQuotes(coroutineDispatcher: CoroutineDispatcher?) {
        viewModelScope.launch(coroutineDispatcher ?: return) {
            val data = categoriesRepository.fetchQuotesData(coroutineDispatcher)
            data?.let { _quotesData.value = (it) }


        }
    }
}
