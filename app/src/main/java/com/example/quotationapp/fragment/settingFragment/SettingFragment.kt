package com.example.quotationapp.fragment.settingFragment

import RateUsDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentSettingBinding
import com.example.quotationapp.fragment.quotesNumberFragment.QuotesRepository
import com.example.quotationapp.utlis.shareApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    val binding get() = _binding
    val viewModel: SettingViewModel by viewModels()
    var rateUsDialog: RateUsDialog? = null
    var dailyCount: Int = 6

    @Inject
    lateinit var quotesRepository: QuotesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rateUsDialog = RateUsDialog(activity = activity ?: return)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            dailyCount = quotesRepository.getQuotesNumber()
            Log.e("dailyQuotesCount", "onViewCreated: dailyQuoteCount $dailyCount")
            clickListener(this@SettingFragment)
            observeViewmodel()
            txtDailyCountValue.text = String.format("%02d", dailyCount)
        }
    }

    private fun observeViewmodel() {
        viewModel.dailyQuotesShow.observe(viewLifecycleOwner) { isEnabled ->
            binding?.icShowDailyQuotesSwitch?.setImageResource(
                if (isEnabled) R.drawable.switch_off else R.drawable.switch_on
            )
        }

        viewModel.notificationShow.observe(viewLifecycleOwner) { isEnabled ->
            binding?.icShowNotificationsQuotesSwitch?.setImageResource(
                if (isEnabled) R.drawable.switch_on else R.drawable.switch_off
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateDailyCount(newCount: Int) {
        dailyCount = newCount
        binding?.txtDailyCountValue?.text = String.format("%02d", dailyCount)
        quotesRepository.setQuotesNumber(dailyCount)
    }
}

