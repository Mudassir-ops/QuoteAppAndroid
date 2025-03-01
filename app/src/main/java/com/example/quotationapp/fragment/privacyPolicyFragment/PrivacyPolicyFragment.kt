package com.example.quotationapp.fragment.privacyPolicyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentPrivacyPolicyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyPolicyFragment : Fragment() {
    private var _binding: FragmentPrivacyPolicyBinding? = null
    private val binding get() = _binding
    private val privacyViewModel: PrivacyViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
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


    private fun FragmentPrivacyPolicyBinding?.clickListener(){
        binding?.apply {
            btnAccept.setOnClickListener {
                privacyViewModel.setNextButtonPrivacyPolicy(true)
                if (findNavController().currentDestination?.id == R.id.privacyPolicyFragment){
                    findNavController().navigate(R.id.action_privacyPolicyFragment_to_permissionFragment)
                }
            }
        }
    }
}