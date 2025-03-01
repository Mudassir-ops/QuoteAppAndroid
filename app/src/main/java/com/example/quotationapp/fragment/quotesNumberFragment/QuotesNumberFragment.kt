package com.example.quotationapp.fragment.quotesNumberFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentPrivacyPolicyBinding
import com.example.quotationapp.databinding.FragmentQuotesNumberBinding
import com.example.quotationapp.fragment.privacyPolicyFragment.PrivacyViewModel
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class QuotesNumberFragment : Fragment() {
    private var _binding: FragmentQuotesNumberBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: QuotesAdapter
    private val privacyViewModel: PrivacyViewModel by activityViewModels()
    private val numbers = (2..10 step 2).toList()
    private var selectedValue: Int = 6
    @Inject
    lateinit var quotesRepository: QuotesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuotesNumberBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = QuotesAdapter(context?:return,numbers,selectedValue) { selected ->
            selectedValue = selected
            Log.d("Selected Number", "User selected: $selected")
        }

        binding?.apply {
            horizontalRecyclerview.layoutManager = GridLayoutManager(context?:return, 5)
            horizontalRecyclerview.adapter = this@QuotesNumberFragment.adapter
            clickListener()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentQuotesNumberBinding?.clickListener(){
        binding?.apply {
            btnNext.setOnClickListener {
               quotesRepository.setQuotesNumber(selectedValue)
                privacyViewModel.setNextButtonQuotesNumber(true)
                Log.e("quotesNumber", "onViewCreated: quotesNumber set $selectedValue", )
                if (findNavController().currentDestination?.id == R.id.quotesNumberFragment){
                    findNavController().navigate(R.id.action_quotesNumberFragment_to_welcomeFragment)
                }
            }
        }
    }

}