package com.example.quotationapp.fragment.homeFragment

import android.annotation.SuppressLint
import android.app.DirectAction
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.quotationapp.R
import com.example.quotationapp.data.QuotesDao
import com.example.quotationapp.databinding.FragmentCategoriesBinding
import com.example.quotationapp.databinding.FragmentHomeBinding
import com.example.quotationapp.json.Quotes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: HomeAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var quotesDao: QuotesDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = HomeAdapter(context = context?:return,
            quotes = listOf(),
            quotesDao = quotesDao,
            onItemClick = {selectedQuote->
                Log.d("HomeFragment", "Selected Quote: ${selectedQuote.text} - ${selectedQuote.author}")

            })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            clickListener()
            homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            homeRecyclerView.adapter = adapter
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding?.homeRecyclerView)
            homeViewModel.quotesList.observe(viewLifecycleOwner) { quotes ->
//                adapter.updateData(quotes)
//                quotes.forEach { quote ->
//                    Log.d("HomeFragment", "Quote: ${quote.text} - ${quote.author}")
//                }

                val shuffledQuotes = quotes.shuffled()
                adapter.updateData(shuffledQuotes)
                shuffledQuotes.forEach { quote ->
                    Log.d("HomeFragment", "Quote: ${quote.text} - ${quote.author}")
                }
            }

            homeViewModel.loadQuotes()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentHomeBinding?.clickListener(){
        binding?.apply {
            icCategory.setOnClickListener {
               findNavController().navigate(R.id.categoriesFragment)
            }
        }
    }


}