package com.example.quotationapp.fragment.homeFragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quotationapp.R
import com.example.quotationapp.data.QuotesDao
import com.example.quotationapp.data.QuotesEntity
import com.example.quotationapp.databinding.ItemHomeBinding
import com.example.quotationapp.json.Quotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeAdapter(
    val context: Context,
    var quotes: List<Quotes>,
    private val quotesDao: QuotesDao,
    private val onItemClick:(Quotes)->Unit

) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = quotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = quotes[position]
        holder.binding.txtAuthorName.text = dataModel.author
        holder.binding.txtQuotes.text = dataModel.text

        CoroutineScope(Dispatchers.IO).launch {
            val isFavorite = quotesDao.isFavorite(dataModel.text ?: return@launch, dataModel.author ?: return@launch)

            CoroutineScope(Dispatchers.Main).launch {
                holder.binding.icFav.setImageResource(
                    if (isFavorite) R.drawable.ic_favourite_new else R.drawable.ic_favourite
                )
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(dataModel)
        }
        holder.binding.icCopy.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, dataModel.text)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
        holder.binding.icFav.setOnClickListener {
            saveQuoteToDatabase(dataModel,holder)
        }
    }
    fun updateData(newQuotes: List<Quotes>) {
        quotes = newQuotes
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    private fun saveQuoteToDatabase(quote: Quotes, holder: ViewHolder) {
        CoroutineScope(Dispatchers.IO).launch {
            val isFavorite = quotesDao.isFavorite(quote.text ?: return@launch, quote.author ?: return@launch)

            if (isFavorite) {
                quotesDao.deleteQuoteByText(quote.text ?: return@launch)
            } else {
                val quoteEntity = QuotesEntity(
                    quotesText = quote.text ?: return@launch,
                    authorName = quote.author ?: return@launch,
                    isFavourite = true
                )
                quotesDao.insertQuotesData(quoteEntity)
            }

            CoroutineScope(Dispatchers.Main).launch {
                holder.binding.icFav.setImageResource(
                    if (isFavorite) R.drawable.ic_favourite else R.drawable.ic_favourite_new
                )
                notifyDataSetChanged()
            }
        }
    }
}
