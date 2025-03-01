package com.example.quotationapp.fragment.privacyPolicyFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrivacyViewModel @Inject constructor(
    private val privacyPolicyRepository: PrivacyPolicyRepository
) : ViewModel() {

    private val _isNextButtonPrivacyPolicy = MutableLiveData<Boolean>()
    private val _isNextButtonPermission = MutableLiveData<Boolean>()
    private val _isNextButtonName = MutableLiveData<Boolean>()
    private val _isNextButtonGender = MutableLiveData<Boolean>()
    private val _isNextButtonQuotesNumber = MutableLiveData<Boolean>()
    private val _isNextButtonTimePeriod = MutableLiveData<Boolean>()
    private val _isNextButtonNotification = MutableLiveData<Boolean>()


    fun setNextButtonPrivacyPolicy(isNextButtonPrivacyPolicy: Boolean) {
        privacyPolicyRepository.setNextButtonPrivacyPolicy(isNextButtonPrivacyPolicy)
        _isNextButtonPrivacyPolicy.value = isNextButtonPrivacyPolicy

    }

    fun setNextButtonPermission(isNextButtonPermission: Boolean) {
        privacyPolicyRepository.setNextButtonPermission(isNextButtonPermission)
        _isNextButtonPermission.value = isNextButtonPermission

    }

    fun setNextButtonName(isNextButtonName: Boolean) {
        privacyPolicyRepository.setNextButtonNameScreen(isNextButtonName)
        _isNextButtonName.value = isNextButtonName

    }

    fun setNextButtonGender(isNextButtonGender: Boolean) {
        privacyPolicyRepository.setNextButtonGender(isNextButtonGender)
        _isNextButtonGender.value = isNextButtonGender

    }

    fun setNextButtonQuotesNumber(isNextButtonQuotesNumber: Boolean) {
        privacyPolicyRepository.setNextButtonQuotesNumber(isNextButtonQuotesNumber)
        _isNextButtonQuotesNumber.value = isNextButtonQuotesNumber

    }

    fun setNextButtonTimePeriod(isNextButtonTimePeriod: Boolean) {
        privacyPolicyRepository.setNextButtonTimePeriod(isNextButtonTimePeriod)
        _isNextButtonTimePeriod.value = isNextButtonTimePeriod

    }

    fun setNextButtonNotification(isNextButtonNotification: Boolean) {
        privacyPolicyRepository.setNextButtonNotification(isNextButtonNotification)
        _isNextButtonNotification.value = isNextButtonNotification

    }

}
