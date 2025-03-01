package com.example.quotationapp.fragment.welcomeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentWelcomeBinding
import com.example.quotationapp.fragment.nameFragment.NameRepository
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding
    @Inject
    lateinit var nameRepository: NameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
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

    private fun FragmentWelcomeBinding?.clickListener(){
        binding?.apply {
            val userName = nameRepository.getUserName()
            val userAge = nameRepository.getUserAge()
            if (userName != null) {
                binding?.txtWelcome?.text = "Welcome, $userName"
            }
            btnContinue.setOnClickListener {
                if (findNavController().currentDestination?.id == R.id.welcomeFragment){
                    findNavController().navigate(R.id.action_welcomeFragment_to_mainFragment)
                }
            }
        }
    }

}