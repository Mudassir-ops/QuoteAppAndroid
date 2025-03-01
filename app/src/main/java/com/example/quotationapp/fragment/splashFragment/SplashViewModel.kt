package com.example.quotationapp.fragment.splashFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.quotationapp.fragment.privacyPolicyFragment.PrivacyPolicyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val privacyPolicyRepository: PrivacyPolicyRepository
) : ViewModel() {


    suspend fun getNextButtonPrivacyPolicy(): Boolean {
        return withContext(Dispatchers.IO) {
            privacyPolicyRepository.getNextButtonPrivacyPolicy()
        }
    }

    suspend fun getNextButtonPermission(): Boolean {
        return withContext(Dispatchers.IO) {
            privacyPolicyRepository.getNextButtonPermission()
        }
    }

    suspend fun getNextButtonNameScreen(): Boolean {
        return withContext(Dispatchers.IO) {
            privacyPolicyRepository.getNextButtonNameScreen()
        }
    }
    suspend fun getNextButtonGender(): Boolean {
        return withContext(Dispatchers.IO) {
            privacyPolicyRepository.getNextButtonGender()
        }
    }
    suspend fun getNextButtonQuotesNumber(): Boolean {
        return withContext(Dispatchers.IO) {
            privacyPolicyRepository.getNextButtonQuotesNumber()
        }
    }
    suspend fun getNextButtonTimePeriod(): Boolean {
        return withContext(Dispatchers.IO) {
            privacyPolicyRepository.getNextButtonTimePeriod()
        }
    }
    suspend fun getNextButtonNotification(): Boolean {
        return withContext(Dispatchers.IO) {
            privacyPolicyRepository.getNextButtonNotification()
        }
    }

}
