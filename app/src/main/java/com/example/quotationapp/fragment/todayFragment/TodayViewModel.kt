package com.example.quotationapp.fragment.todayFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quotationapp.json.Quotes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TodayViewModel @Inject constructor(
    private val todayRepository: TodayRepository
) : ViewModel() {

    private val _quotesList = MutableLiveData<List<Quotes>>()
    val quotesList: LiveData<List<Quotes>> get() = _quotesList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadQuotesFromFirestore() {
        _isLoading.postValue(true)
        todayRepository.getQuotesFromFirestore { quotes ->
            _quotesList.postValue(quotes)
            _isLoading.postValue(false)
        }
    }
}

