package com.example.quotationapp.fragment.settingFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {

    private val _dailyQuotesShow = MutableLiveData<Boolean>()
    private val _notificationShow = MutableLiveData<Boolean>()
    val dailyQuotesShow: LiveData<Boolean> get() = _dailyQuotesShow
    val notificationShow: LiveData<Boolean> get() = _notificationShow


    init {
        _dailyQuotesShow.value = settingRepository.getDailyQuotes()
        _notificationShow.value = settingRepository.getNotificationQuotes()
    }

    fun setDailyQuotes(isEnabled: Boolean) {
        settingRepository.setShowDailyQuotes(isEnabled)
        _dailyQuotesShow.value = isEnabled
    }

    fun setNotificationQuotes(isEnabled: Boolean) {
        settingRepository.setNotificationQuotes(isEnabled)
        _notificationShow.value = isEnabled
    }
}

