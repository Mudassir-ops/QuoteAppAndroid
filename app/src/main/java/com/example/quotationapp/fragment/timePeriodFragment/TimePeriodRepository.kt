package com.example.quotationapp.fragment.timePeriodFragment

import com.example.quotationapp.R
import jakarta.inject.Inject

class TimePeriodRepository @Inject constructor() {

    fun getTimePeriodList() = listOf(
       TimePeriodDataModel(
           imgBackground = R.drawable.pic_morning,
           icChecked = R.drawable.ic_checked,
           icTiming = R.drawable.ic_morning_new,
           txtTitle = "Morning",
           txtTiming = "06 AM - 12 AM"
       ),

        TimePeriodDataModel(
            imgBackground = R.drawable.pic_evening,
            icChecked = R.drawable.ic_unchecked,
            icTiming = R.drawable.ic_evening_new,
            txtTitle = "Evening",
            txtTiming = "18 PM - 00 PM"
        ),
        TimePeriodDataModel(
            imgBackground = R.drawable.pic_afternoon,
            icChecked = R.drawable.ic_unchecked,
            icTiming = R.drawable.ic_afternoon_new,
            txtTitle = "Afternoon",
            txtTiming = "12 PM - 18 PM"
        ),
        TimePeriodDataModel(
            imgBackground = R.drawable.pic_night,
            icChecked = R.drawable.ic_unchecked,
            icTiming = R.drawable.ic_night,
            txtTitle = "Night",
            txtTiming = "00 AM - 06 AM"
        ),
    )
}