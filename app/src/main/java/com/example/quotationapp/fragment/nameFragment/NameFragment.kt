package com.example.quotationapp.fragment.nameFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentNameBinding
import com.example.quotationapp.fragment.privacyPolicyFragment.PrivacyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameFragment : Fragment() {

    private var _binding: FragmentNameBinding? = null
    private val binding get() = _binding
    private val  nameViewModel:NameViewModel by viewModels()
    private val privacyViewModel: PrivacyViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNameBinding.inflate(inflater, container, false)
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

    private fun FragmentNameBinding?.clickListener() {
        binding?.apply {
            btnNext.setOnClickListener {
                val newName = edName.text.toString().trim()
                val userAge = edAge.text.toString().trim()
                if (newName.isNotEmpty() && userAge.isNotEmpty()) {
                    nameViewModel.setUserName(newName)
                    nameViewModel.setUserAge(userAge)
                    privacyViewModel.setNextButtonName(true)
                    if (findNavController().currentDestination?.id == R.id.nameFragment) {
                        findNavController().navigate(R.id.action_nameFragment_to_genderFragment)
                    }
                } else {
                    Toast.makeText(requireContext(), "Name and Age cannot be empty", Toast.LENGTH_SHORT).show()
                }

            }

            }

        }
    }
