package com.example.quotationapp.fragment.categoriesFragment

import android.content.Context
import com.example.quotationapp.json.QuotesBaseClass
import com.example.quotationapp.json.QuotesData
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CategoriesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun fetchQuotesData(coroutineDispatcher: CoroutineDispatcher?): QuotesData? {
        return withContext(coroutineDispatcher ?: return null) {
            try {
                val inputStream = context.assets.open("structured_quotes_with_categories.json")
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                val gson = Gson()
                val quotesData = gson.fromJson(jsonString, QuotesBaseClass::class.java)
                quotesData.quotesData
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}