package com.example.quotationapp.fragment.settingFragment

import android.content.SharedPreferences
import jakarta.inject.Inject

class SettingRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val KEY_SHOW_DAILY_QUOTES = "show_daily_quotes"
    private val KEY_NOTIFICATION_QUOTES = "notification_quotes"

    fun setShowDailyQuotes(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_SHOW_DAILY_QUOTES, isEnabled).apply()
    }

    fun getDailyQuotes(): Boolean {
        return sharedPreferences.getBoolean(KEY_SHOW_DAILY_QUOTES, false)
    }

    fun setNotificationQuotes(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_NOTIFICATION_QUOTES, isEnabled).apply()
    }

    fun getNotificationQuotes(): Boolean {
        return sharedPreferences.getBoolean(KEY_NOTIFICATION_QUOTES, false)
    }
}
