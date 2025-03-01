package com.example.quotationapp.fragment.favouriteFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotationapp.data.QuotesDao
import com.example.quotationapp.databinding.FragmentFavouriteBinding
import com.example.quotationapp.utlis.gone
import com.example.quotationapp.utlis.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding
    private val favouriteViewModel: FavouriteViewModel by viewModels()

    @Inject
    lateinit var quotesDao: QuotesDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFavouriteQuotes()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding?.favRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeFavouriteQuotes() {
        favouriteViewModel.favouriteQuotes.observe(viewLifecycleOwner) { favList ->
            if (favList.isNullOrEmpty()) {
                binding?.apply {
                    txtFavourite.visible()
                    favRecyclerView.gone()
                }
            } else {
                binding?.apply {
                    txtFavourite.gone()
                    favRecyclerView.visible()
                }

                val adapter = FavouriteAdapter(
                    favList.toMutableList(),
                    requireContext(),
                    quotesDao,
                    favouriteViewModel
                )
                binding?.favRecyclerView?.adapter = adapter
            }
        }
    }
}
