package com.example.quotationapp.fragment.quotesNumberFragment

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotationapp.R
import com.example.quotationapp.databinding.ItemQuoteBinding
import com.example.quotationapp.utlis.gone
import com.example.quotationapp.utlis.visible

class QuotesAdapter(
    private val context: Context,
    private val items: List<Int>,
    private val defaultSelectedValue: Int,
    private val onItemSelected: (Int) -> Unit) :
    RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {

    private var selectedPosition = -1

    init {
        selectedPosition = items.indexOf(defaultSelectedValue)
    }
    inner class QuotesViewHolder(val binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(value: Int, isSelected: Boolean) {
            binding.apply {
                tvNumber.text = value.toString()
                tvNumber.setTextColor(if (isSelected) context.getColor(R.color.selected_color) else context.getColor(R.color.un_selected_color))
                tvNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, if (isSelected) 30f else 16f)
                if (isSelected) icSelected.visible() else icSelected.gone()

            }


            itemView.setOnClickListener {
                val previousSelected = selectedPosition
                selectedPosition = adapterPosition
                onItemSelected(value)
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(items[position], position == selectedPosition)
    }

    override fun getItemCount() = items.size
}
