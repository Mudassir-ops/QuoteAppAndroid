package com.example.quotationapp.fragment.nameFragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    private val nameRepository: NameRepository
) : ViewModel() {
    private val _isUserName = MutableLiveData<String>()

    private val _isUserAge = MutableLiveData<String>()

    fun setUserName(userName:String){
        nameRepository.setUserName(userName)
        _isUserName.value = userName
    }

    fun setUserAge(userAge:String){
        nameRepository.setUserAge(userAge)
        _isUserAge.value = userAge
    }


}
