package com.example.quotationapp.fragment.timePeriodFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentPrivacyPolicyBinding
import com.example.quotationapp.databinding.FragmentTimePeriodBinding
import com.example.quotationapp.fragment.privacyPolicyFragment.PrivacyViewModel
import com.example.quotationapp.fragment.quotesNumberFragment.QuotesRepository
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class TimePeriodFragment : Fragment() {
    private var _binding: FragmentTimePeriodBinding? = null
    private val binding get() = _binding
    private var adapter: TimePeriodAdapter? = null
    private val viewModel: TimePeriodViewModel by viewModels()
    private val privacyViewModel: PrivacyViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TimePeriodAdapter(context =context?:return,
            timePeriodList = viewModel.getTimePeriodList(),
            onItemClicked = { position->
                Log.e("itemClicked", "onCreate: check Position $position" )

            })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimePeriodBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            clickListener()
            observerViewModel()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun FragmentTimePeriodBinding?.clickListener(){
        binding?.apply {
            btnNext.setOnClickListener {
                privacyViewModel.setNextButtonTimePeriod(true)
                if (findNavController().currentDestination?.id == R.id.timePeriodFragment){
                    findNavController().navigate(R.id.action_timePeriodFragment_to_notificationFragment)
                }
            }
        }
    }

    private fun observerViewModel() {
        viewModel.timePeriodList.observe(viewLifecycleOwner) { languageList ->
            if (!languageList.isNullOrEmpty()) {
                adapter?.updateTimePeriodList(languageList)
            }
            if (binding?.timePeriodRecyclerview?.adapter == null)
                binding?.timePeriodRecyclerview?.adapter = adapter
        }
    }

}