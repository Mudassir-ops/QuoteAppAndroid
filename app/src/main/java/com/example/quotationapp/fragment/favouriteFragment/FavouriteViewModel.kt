package com.example.quotationapp.fragment.favouriteFragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.quotationapp.data.QuotesDao
import com.example.quotationapp.data.QuotesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: FavouriteRepository
) : ViewModel() {
    val favouriteQuotes = repository.getFavouriteQuotes().asLiveData()

    private val _isFavouriteListEmpty = MutableLiveData<Boolean>()
    val isFavouriteListEmpty: LiveData<Boolean> = _isFavouriteListEmpty

    fun setFavouriteListEmpty() {
        _isFavouriteListEmpty.postValue(true)
    }


//    val favouriteQuotes: LiveData<List<QuotesEntity>> =
//        quotesDao.getAllFavouriteQuotesFlow().asLiveData()
}
