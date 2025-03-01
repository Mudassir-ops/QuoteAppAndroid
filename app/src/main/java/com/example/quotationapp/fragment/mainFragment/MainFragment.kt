package com.example.quotationapp.fragment.mainFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentMainBinding
import com.example.quotationapp.fragment.favouriteFragment.FavouriteFragment
import com.example.quotationapp.fragment.homeFragment.HomeFragment
import com.example.quotationapp.fragment.settingFragment.SettingFragment
import com.example.quotationapp.fragment.todayFragment.TodayFragment
import kotlin.system.exitProcess


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding?.apply {

                        val currentFragment = childFragmentManager.findFragmentById(R.id.framelayout)
                        if (currentFragment !is HomeFragment) {
                            replaceFragment(HomeFragment())
                        } else {
                            exitProcess(0)
                        }
                    }

            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        replaceFragment(HomeFragment())
        clickListener()
    }

    private fun clickListener() {
        binding?.apply {
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.txtHome ->{
                        replaceFragment(HomeFragment())
                    }
                    R.id.txtFavourite -> {
                        replaceFragment(FavouriteFragment())
                    }
                    R.id.txtToday ->{
                        replaceFragment(TodayFragment())
                    }

                    R.id.txtSetting ->{
                        replaceFragment(SettingFragment())
                    }
                    else -> false
                }
                true
            }

        }
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        childFragmentManager.beginTransaction()
            .replace(R.id.framelayout, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
        return true
    }

}