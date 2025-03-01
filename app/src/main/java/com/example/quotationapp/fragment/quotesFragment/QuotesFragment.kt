package com.example.quotationapp.fragment.quotesFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.quotationapp.data.QuotesDao
import com.example.quotationapp.databinding.FragmentQuotesBinding
import com.example.quotationapp.fragment.homeFragment.HomeAdapter
import com.example.quotationapp.fragment.homeFragment.HomeViewModel
import com.example.quotationapp.json.Quotes
import com.example.quotationapp.utlis.BooleanObjects.KEY_CATEGORY_NAME
import com.example.quotationapp.utlis.BooleanObjects.KEY_IMAGE_RESOURCE
import com.example.quotationapp.utlis.BooleanObjects.KEY_QUOTES
import com.example.quotationapp.utlis.parcelableArrayList
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class QuotesFragment : Fragment() {
    private var _binding: FragmentQuotesBinding? = null
    private val binding get() = _binding
    private  var quotesAdapter: QuotesAdapter?=null
    private var quotesList: ArrayList<Quotes>? = null

    @Inject
    lateinit var quotesDao: QuotesDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quotesList = arguments?.parcelableArrayList<Quotes>(KEY_QUOTES)
        quotesList = quotesList?.shuffled()?.toCollection(ArrayList())
        quotesAdapter = quotesList?.let {
            QuotesAdapter(quotes = it,quotesDao = quotesDao,context?:return)
        }
        Log.e("quotes", "QuotesFragment: quotesList Size ${quotesList?.size}", )
        Log.e("quotes", "QuotesFragment: get quotes List $quotesList", )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuotesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryName = arguments?.getString(KEY_CATEGORY_NAME)
        binding?.txtCategoryName?.text = categoryName
        binding?.icBack?.setOnClickListener {
            findNavController().navigateUp()
        }
        setupRecyclerView()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setupRecyclerView() {
        binding?.quotesRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = quotesAdapter
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding?.quotesRecyclerView)
        }
    }

}