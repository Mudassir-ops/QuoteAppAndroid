package com.example.quotationapp.fragment.quotesFragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quotationapp.R
import com.example.quotationapp.data.QuotesDao
import com.example.quotationapp.data.QuotesEntity
import com.example.quotationapp.databinding.ItemHomeBinding
import com.example.quotationapp.fragment.homeFragment.HomeAdapter.ViewHolder
import com.example.quotationapp.json.Quotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesAdapter(
    var quotes: ArrayList<Quotes>,
    private val quotesDao: QuotesDao,
    val context: Context,
) : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = quotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = quotes[position]
        holder.binding.txtQuotes.text = dataModel.text
        holder.binding.txtAuthorName.text = dataModel.author

        CoroutineScope(Dispatchers.IO).launch {
            val isFavorite = quotesDao.isFavorite(dataModel.text ?: return@launch, dataModel.author ?: return@launch)

            CoroutineScope(Dispatchers.Main).launch {
                holder.binding.icFav.setImageResource(
                    if (isFavorite) R.drawable.ic_favourite_new else R.drawable.ic_favourite
                )
            }
        }

        holder.binding.icCopy.setOnClickListener {
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Quote", dataModel.text)
            clipboardManager.setPrimaryClip(clip)
            Toast.makeText(context, "Text copied!", Toast.LENGTH_SHORT).show()
        }
        holder.binding.icFav.setOnClickListener {
            saveQuoteToDatabase(dataModel,holder)
        }
    }

    fun updateData(newQuotes: ArrayList<Quotes>) {
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
