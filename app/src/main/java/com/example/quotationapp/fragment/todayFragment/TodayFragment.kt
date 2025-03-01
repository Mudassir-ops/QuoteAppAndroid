package com.example.quotationapp.fragment.todayFragment

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.quotationapp.data.QuotesDao
import com.example.quotationapp.databinding.FragmentTodayBinding
import com.example.quotationapp.dialog.TodayQuotesDialog
import com.example.quotationapp.fragment.quotesNumberFragment.QuotesRepository
import com.example.quotationapp.fragment.settingFragment.SettingRepository
import com.example.quotationapp.json.Quotes
import com.example.quotationapp.notification.AlarmScheduler
import com.example.quotationapp.utlis.gone
import com.example.quotationapp.utlis.toast
import com.example.quotationapp.utlis.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private lateinit var todayAdapter: TodayAdapter
    private val todayViewModel: TodayViewModel by viewModels()
    private var todayQuotesDialog: TodayQuotesDialog? = null
    @Inject
    lateinit var quotesDao: QuotesDao

    @Inject
    lateinit var settingRepository: SettingRepository

    @Inject
    lateinit var quotesRepository: QuotesRepository

    lateinit var calendar: Calendar
    var title = "Title"
    var description = "Description"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calendar = Calendar.getInstance()
        todayAdapter = TodayAdapter(emptyList(), quotesDao, onNotificationClick = { selectedQuotes->
            showNotificationDialog(selectedQuotes)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestNotificationPermission()
        setupRecyclerView()
        quotesRepository.quotesNumber.observe(viewLifecycleOwner) { dailyQuoteLimit ->
            loadQuotes(dailyQuoteLimit)
        }

    }

    private fun setupRecyclerView() {
        binding.todayRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todayAdapter
        }

        PagerSnapHelper().attachToRecyclerView(binding.todayRecyclerView)
    }


    private fun loadQuotes(dailyQuoteLimit: Int) {
        val isFirebaseEnabled = settingRepository.getDailyQuotes()

        if (!isFirebaseEnabled) {
            binding.txtNoList.gone()
            binding.todayRecyclerView.visible()

            todayViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                binding.progressBar.apply { if (isLoading) visible() else gone() }
            }

            todayViewModel.quotesList.observe(viewLifecycleOwner) { quotes ->
                val limitedQuotes = quotes.take(dailyQuoteLimit) // List dynamically update hogi
                todayAdapter.updateData(limitedQuotes)
            }

            todayViewModel.loadQuotesFromFirestore()
        } else {
            todayAdapter.updateData(emptyList())
            binding.txtNoList.visible()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showNotificationDialog(quote: Quotes) {
        todayQuotesDialog = TodayQuotesDialog(activity = activity ?: return) { hour, minutes, amPm ->
            val formattedHour = if (amPm == "PM" && hour != 12) hour + 12 else if (amPm == "AM" && hour == 12) 0 else hour
            val title = quote.author.toString()
            val description = quote.text.toString()

            Log.e("onTimeSelected", "Time : $hour:$minutes $amPm")

            AlarmScheduler.scheduleNotification(requireContext(), formattedHour, minutes, title, description)
        }
        todayQuotesDialog?.show()
    }


    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }
    }

}
