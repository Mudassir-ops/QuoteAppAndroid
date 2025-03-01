package com.example.quotationapp.fragment.homeFragment

import android.app.Application
import com.example.quotationapp.json.Quotes
import com.example.quotationapp.json.QuotesBaseClass
import com.google.gson.Gson
import jakarta.inject.Inject

class HomeRepository @Inject constructor(private val application: Application) {

    fun getQuotesFromAssets(): List<Quotes> {
        val json = application.assets.open("structured_quotes_with_categories.json").bufferedReader().use { it.readText() }
        val quotesBaseClass = Gson().fromJson(json, QuotesBaseClass::class.java)
        return quotesBaseClass.quotesData?.categories?.flatMap { it.quotes ?: emptyList() } ?: emptyList()
    }
}
