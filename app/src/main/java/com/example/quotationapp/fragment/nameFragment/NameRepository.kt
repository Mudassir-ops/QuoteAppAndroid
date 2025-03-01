package com.example.quotationapp.fragment.nameFragment

import android.content.SharedPreferences
import com.example.quotationapp.utlis.SharedPreferencesObjects.userAge
import com.example.quotationapp.utlis.SharedPreferencesObjects.userName
import jakarta.inject.Inject

class NameRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {


    fun setUserName(username: String) {
        sharedPreferences.edit().putString(userName, username).apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(userName, null)
    }

    fun setUserAge(username: String) {
        sharedPreferences.edit().putString(userAge, username).apply()
    }

    fun getUserAge(): String? {
        return sharedPreferences.getString(userAge, null)
    }
}