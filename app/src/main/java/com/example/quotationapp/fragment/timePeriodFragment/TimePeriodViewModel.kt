package com.example.quotationapp.fragment.timePeriodFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimePeriodViewModel @Inject constructor(
    private val timePeriodRepository: TimePeriodRepository
) : ViewModel() {
    val timePeriodList = MutableLiveData<List<TimePeriodDataModel>?>()

    init {
        timePeriodList.value = timePeriodRepository.getTimePeriodList()
    }

    fun getTimePeriodList(): List<TimePeriodDataModel> {
        return timePeriodRepository.getTimePeriodList()
    }

}
