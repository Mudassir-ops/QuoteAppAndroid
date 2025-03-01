package com.example.quotationapp.fragment.privacyPolicyFragment

import android.content.SharedPreferences
import com.example.quotationapp.utlis.BooleanObjects.genderScreenButton
import com.example.quotationapp.utlis.BooleanObjects.nameScreenButton
import com.example.quotationapp.utlis.BooleanObjects.notificationScreenButton
import com.example.quotationapp.utlis.BooleanObjects.permissionScreenButton
import com.example.quotationapp.utlis.BooleanObjects.privacyPolicyScreenButton
import com.example.quotationapp.utlis.BooleanObjects.quoteScreenButton
import com.example.quotationapp.utlis.BooleanObjects.timePeriodScreenButton
import jakarta.inject.Inject

class PrivacyPolicyRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun setNextButtonPrivacyPolicy(isDoneClick: Boolean) {
        sharedPreferences.edit().putBoolean(privacyPolicyScreenButton, isDoneClick).apply()
    }

    fun getNextButtonPrivacyPolicy(): Boolean {
        return sharedPreferences.getBoolean(privacyPolicyScreenButton, false)
    }

    fun setNextButtonPermission(isDoneClick: Boolean) {
        sharedPreferences.edit().putBoolean(permissionScreenButton, isDoneClick).apply()
    }

    fun getNextButtonPermission(): Boolean {
        return sharedPreferences.getBoolean(permissionScreenButton, false)
    }

    fun setNextButtonNameScreen(isDoneClick: Boolean) {
        sharedPreferences.edit().putBoolean(nameScreenButton, isDoneClick).apply()
    }

    fun getNextButtonNameScreen(): Boolean {
        return sharedPreferences.getBoolean(nameScreenButton, false)
    }

    fun setNextButtonGender(isDoneClick: Boolean) {
        sharedPreferences.edit().putBoolean(genderScreenButton, isDoneClick).apply()
    }

    fun getNextButtonGender(): Boolean {
        return sharedPreferences.getBoolean(genderScreenButton, false)
    }

    fun setNextButtonQuotesNumber(isDoneClick: Boolean) {
        sharedPreferences.edit().putBoolean(quoteScreenButton, isDoneClick).apply()
    }

    fun getNextButtonQuotesNumber(): Boolean {
        return sharedPreferences.getBoolean(quoteScreenButton, false)
    }

    fun setNextButtonTimePeriod(isDoneClick: Boolean) {
        sharedPreferences.edit().putBoolean(timePeriodScreenButton, isDoneClick).apply()
    }

    fun getNextButtonTimePeriod(): Boolean {
        return sharedPreferences.getBoolean(timePeriodScreenButton, false)
    }

    fun setNextButtonNotification(isDoneClick: Boolean) {
        sharedPreferences.edit().putBoolean(notificationScreenButton, isDoneClick).apply()
    }

    fun getNextButtonNotification(): Boolean {
        return sharedPreferences.getBoolean(notificationScreenButton, false)
    }


}
