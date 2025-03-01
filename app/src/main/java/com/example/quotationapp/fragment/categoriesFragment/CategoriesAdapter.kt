package com.example.quotationapp.fragment.categoriesFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotationapp.databinding.ItemCategoryBinding
import com.example.quotationapp.json.Categories
import com.example.quotationapp.json.Quotes

class CategoriesAdapter(
    private var quotes: ArrayList<Categories>,
    private val callback: (ArrayList<Quotes>, Int, String) -> Unit


) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = quotes.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = quotes[position]
        holder.binding.txtTitle.text = dataModel.categoryName.toString()
        holder.binding.imgBackground.setImageResource(dataModel.imageResource)

        holder.itemView.setOnClickListener {
            dataModel.quotes?.let { it1 ->
                callback.invoke(
                    it1, dataModel.imageResource,
                    dataModel.categoryName.toString()
                )
            }
        }
    }


    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<Categories>) {
        quotes = list
        notifyDataSetChanged()
    }
}