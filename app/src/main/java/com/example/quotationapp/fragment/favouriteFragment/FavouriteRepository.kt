package com.example.quotationapp.fragment.favouriteFragment

import com.example.quotationapp.data.QuotesDao
import javax.inject.Inject

class FavouriteRepository @Inject constructor(private val quotesDao: QuotesDao) {
    fun getFavouriteQuotes() = quotesDao.getAllFavouriteQuotesFlow()
}