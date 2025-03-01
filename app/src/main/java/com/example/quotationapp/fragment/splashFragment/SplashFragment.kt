package com.example.quotationapp.fragment.splashFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentPrivacyPolicyBinding
import com.example.quotationapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding
    private val splashViewModel:SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNavigation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkNavigation() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            val destination = when {
                !splashViewModel.getNextButtonPrivacyPolicy() -> R.id.action_splashFragment_to_privacyPolicyFragment
                !splashViewModel.getNextButtonPermission() -> R.id.action_splashFragment_to_permissionFragment
                !splashViewModel.getNextButtonNameScreen() -> R.id.action_splashFragment_to_nameFragment
                !splashViewModel.getNextButtonGender() -> R.id.action_splashFragment_to_genderFragment
                !splashViewModel.getNextButtonQuotesNumber() -> R.id.action_splashFragment_to_quotesNumberFragment
             //   !splashViewModel.getNextButtonTimePeriod() -> R.id.action_splashFragment_to_timePeriodFragment
             //   !splashViewModel.getNextButtonNotification() -> R.id.action_splashFragment_to_notificationFragment
                else -> R.id.action_splashFragment_to_welcomeFragment
            }

            navigateTo(destination)


           /* if(findNavController().currentDestination?.id ==R.id.splashFragment){
                findNavController().navigate(R.id.action_splashFragment_to_privacyPolicyFragment)
            }*/

        }
    }

    private fun navigateTo(destination: Int) {
        findNavController().navigate(destination)
    }

}