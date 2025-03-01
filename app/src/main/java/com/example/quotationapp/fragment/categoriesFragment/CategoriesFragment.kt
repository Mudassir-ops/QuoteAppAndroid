package com.example.quotationapp.fragment.categoriesFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentCategoriesBinding
import com.example.quotationapp.json.Categories
import com.example.quotationapp.utlis.BooleanObjects.KEY_CATEGORY_NAME
import com.example.quotationapp.utlis.BooleanObjects.KEY_IMAGE_RESOURCE
import com.example.quotationapp.utlis.BooleanObjects.KEY_QUOTES
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding
    private val viewModel:CategoriesViewModel by viewModels()
    private lateinit var categoriesAdapter: CategoriesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoriesAdapter = CategoriesAdapter(arrayListOf(),
            callback = {quotesList, imageResource,categoryName ->
                if (findNavController().currentDestination?.id == R.id.categoriesFragment) {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(KEY_QUOTES, quotesList)
                    bundle.putInt(KEY_IMAGE_RESOURCE,imageResource)
                    bundle.putString(KEY_CATEGORY_NAME, categoryName)
                    Log.e("quotes", "CategoriesFragment: size quotesList ${quotesList.size}", )
                    Log.e("quotes", "CategoriesFragment:  categoryName $categoryName", )
                    findNavController().navigate(R.id.quotesFragment, bundle)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            clickListener()
            observingQuotes()
            setupRecyclerView()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentCategoriesBinding.clickListener(){
        binding?.apply {
            icBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }


    private fun setupRecyclerView() {
        binding?.categoryRecyclerView?.apply {
            layoutManager = GridLayoutManager(context?:return, 3)
            adapter = categoriesAdapter
        }
    }

    private fun observingQuotes() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.quotesData.collect { uiState ->
                    uiState?.let {
                        updateAdapterData(it.categories)
                    }
                }
            }
        }
    }

    private fun updateAdapterData(dataset: ArrayList<Categories>) {
        dataset.forEach { category ->
            category.imageResource = getCategoryImage(category.categoryName.toString())
        }
        categoriesAdapter.submitList(dataset)
    }

    private fun getCategoryImage(categoryName: String): Int {
        return when (categoryName) {
            "Motivational" -> R.drawable.motivational_pic
            "Wisdom" -> R.drawable.wisdom_pic
            "Inspiration" -> R.drawable.inspiration_pic
            "Courage" -> R.drawable.courage_pic
            "Life" -> R.drawable.life_pic
            "Friends" -> R.drawable.friends_pic
            "Relationship" -> R.drawable.relationship_pic
            "Trust" -> R.drawable.trust_pic
            "Failure" -> R.drawable.failure_pic
            else -> R.drawable.motivational_pic
        }
    }

}