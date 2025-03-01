package com.example.quotationapp.fragment.favouriteFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotationapp.data.QuotesDao
import com.example.quotationapp.data.QuotesEntity
import com.example.quotationapp.databinding.ItemFavouriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteAdapter(
    private var favList: MutableList<QuotesEntity>,
    private val context: Context,
    private val quotesDao: QuotesDao,
    private val viewModel: FavouriteViewModel
) : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, this)
    }

    override fun getItemCount(): Int = favList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quoteDataModel = favList[position]
        holder.binding.txtQuotes.text = quoteDataModel.quotesText
        holder.binding.txtAuthorName.text = quoteDataModel.authorName
        holder.bind(quoteDataModel, position, this)
    }

    class ViewHolder(
        val binding: ItemFavouriteBinding,
        private val adapter: FavouriteAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(quotesEntity: QuotesEntity, position: Int, adapter: FavouriteAdapter) {
            binding.icFavourite.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    adapter.quotesDao.deleteQuoteByText(quotesEntity.quotesText)


                    CoroutineScope(Dispatchers.Main).launch {
                        adapter.favList.removeAt(position)
                        adapter.notifyItemRemoved(position)

                        if (adapter.favList.isEmpty()) {
                            adapter.viewModel.setFavouriteListEmpty()
                        }
                    }
                }
            }
        }
    }
}
