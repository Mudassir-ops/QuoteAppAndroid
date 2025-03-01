package com.example.quotationapp.fragment.genderFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentCategoriesBinding
import com.example.quotationapp.databinding.FragmentGenderBinding
import com.example.quotationapp.fragment.nameFragment.NameRepository
import com.example.quotationapp.fragment.privacyPolicyFragment.PrivacyViewModel
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class GenderFragment : Fragment() {
    private var _binding: FragmentGenderBinding? = null
    private val binding get() = _binding
    private val privacyViewModel: PrivacyViewModel by activityViewModels()
    @Inject
    lateinit var nameRepository: NameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenderBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            clickListener()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentGenderBinding?.clickListener(){
        binding?.apply {
            btnNext.setOnClickListener {
                privacyViewModel.setNextButtonGender(true)
                if (findNavController().currentDestination?.id == R.id.genderFragment){
                    findNavController().navigate(R.id.action_genderFragment_to_quotesNumberFragment)
                }
            }
            txtMale.setOnClickListener {
                selectGender(txtMale)
            }
            txtFemale.setOnClickListener {
                selectGender(txtFemale)
            }
            txtOther.setOnClickListener {
                selectGender(txtOther)
            }
        }
    }

    private fun selectGender(selectedView: AppCompatTextView) {
        binding?.apply {
            txtMale.setBackgroundResource(R.drawable.bg_view)
            txtFemale.setBackgroundResource(R.drawable.bg_view)
            txtOther.setBackgroundResource(R.drawable.bg_view)

            selectedView.setBackgroundResource(R.drawable.bg_selected_view)
        }
    }

}