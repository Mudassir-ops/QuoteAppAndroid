package com.example.quotationapp.fragment.settingFragment

import com.example.quotationapp.databinding.FragmentSettingBinding
import com.example.quotationapp.utlis.shareApp

fun FragmentSettingBinding?.clickListener(fragment: SettingFragment){
    fragment.binding?.apply {
        var currentCount = 6

        txtDailyCountMinus.setOnClickListener {
            if (fragment.dailyCount > 2) {
                fragment.dailyCount -= 2
                fragment.updateDailyCount(fragment.dailyCount)
            }
        }

        txtDailyCountPlus.setOnClickListener {
            if (fragment.dailyCount < 12) {
                fragment.dailyCount += 2
                fragment.updateDailyCount(fragment.dailyCount)
            }
        }

        txtDailyCountValue.text = String.format("%02d", currentCount)

        viewRateUs.setOnClickListener {
            fragment.rateUsDialog?.show()
        }
        viewShareApp.setOnClickListener {
            fragment.activity?.shareApp()
        }
        icShowDailyQuotesSwitch.setOnClickListener {
            val newState = !(fragment.viewModel.dailyQuotesShow.value ?: false)
            fragment.viewModel.setDailyQuotes(newState)

        }
        icShowNotificationsQuotesSwitch.setOnClickListener {
            val newState = !(fragment.viewModel.notificationShow.value ?: false)
            fragment.viewModel.setNotificationQuotes(newState)

       }

    }


}

