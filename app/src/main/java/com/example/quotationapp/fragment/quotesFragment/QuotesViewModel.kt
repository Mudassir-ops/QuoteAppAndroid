package com.example.quotationapp.fragment.quotesFragment

import androidx.lifecycle.ViewModel
import com.example.quotationapp.fragment.homeFragment.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

}
