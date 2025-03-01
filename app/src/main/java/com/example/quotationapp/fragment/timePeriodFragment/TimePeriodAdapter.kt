package com.example.quotationapp.fragment.timePeriodFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotationapp.R
import com.example.quotationapp.databinding.ItemTimePeriodBinding

class TimePeriodAdapter(
    private val context: Context,
    private var timePeriodList: List<TimePeriodDataModel>,
    private val onItemClicked: (Int) -> Unit
): RecyclerView.Adapter<TimePeriodAdapter.ViewHolder>() {
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTimePeriodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return timePeriodList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = timePeriodList[position]
        holder.binding.imgBackground.setImageResource(dataModel.imgBackground)
        holder.binding.imgIcon.setImageResource(dataModel.icTiming)
        holder.binding.txtTitle.text = dataModel.txtTitle
        holder.binding.txtTime.text = dataModel.txtTiming
        holder.binding.imgTick.setImageResource(
            if (position == selectedPosition) R.drawable.ic_checked else R.drawable.ic_unchecked
        )

        holder.binding.imgTick.setOnClickListener {
            if (selectedPosition != position) {
                val previousSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
                onItemClicked(position)
            }
        }
    }
    class ViewHolder(val binding: ItemTimePeriodBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateTimePeriodList(newLanguageList: List<TimePeriodDataModel>) {
        timePeriodList = newLanguageList
        notifyDataSetChanged()
    }
}