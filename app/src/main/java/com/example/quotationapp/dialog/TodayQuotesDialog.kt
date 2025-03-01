package com.example.quotationapp.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.quotationapp.R
import com.example.quotationapp.databinding.TodayQuotesDialogBinding

class TodayQuotesDialog(
    activity: Activity,
    private val onTimeSelected: (hour: Int, minute: Int, amPm: String) -> Unit
) : Dialog(activity) {

    private val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val binding = TodayQuotesDialogBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        setCancelable(true)
        setCanceledOnTouchOutside(false)

        val typeface = ResourcesCompat.getFont(context, R.font.roboto_semibold)
        setupPickers(typeface!!)

        binding.apply {
           pickerHour.removeSelectionDivider()
           pickerMinute.removeSelectionDivider()
           pickerAmPm.removeSelectionDivider()


            txtCancel.setOnClickListener { dismiss() }
            txtDone.setOnClickListener {
                val selectedHour = pickerHour.value
                val selectedMinute = pickerMinute.value
                val selectedAmPm = if (pickerAmPm.value == 0) "AM" else "PM"
                onTimeSelected(selectedHour, selectedMinute, selectedAmPm)
                dismiss()
            }
        }
    }

    private fun setupPickers(typeface: Typeface) {
        setNumberPicker(binding.pickerHour, 1, 12, 9, typeface)
        setNumberPicker(binding.pickerMinute, 0, 59, 0, typeface)
        setNumberPicker(binding.pickerAmPm, 0, 1, 1, typeface, arrayOf("AM", "PM"))
    }

    private fun setNumberPicker(
        numberPicker: NumberPicker,
        minValue: Int,
        maxValue: Int,
        defaultValue: Int,
        typeface: Typeface,
        displayedValues: Array<String>? = null
    ) {
        numberPicker.apply {
            this.minValue = minValue
            this.maxValue = maxValue
            this.value = defaultValue
            displayedValues?.let { this.displayedValues = it }
        }
        customizeNumberPicker(numberPicker, typeface)
    }

    @SuppressLint("SoonBlockedPrivateApi")
    private fun customizeNumberPicker(numberPicker: NumberPicker, typeface: Typeface) {
        try {
            val selectorWheelPaintField = numberPicker.javaClass.getDeclaredField("mSelectorWheelPaint")
            selectorWheelPaintField.isAccessible = true
            (selectorWheelPaintField.get(numberPicker) as Paint).typeface = typeface

            val count = numberPicker.childCount
            for (i in 0 until count) {
                val child: View = numberPicker.getChildAt(i)
                if (child is EditText) {
                    child.typeface = typeface
                    child.setTextColor(Color.GRAY)
                }
            }

            numberPicker.setOnValueChangedListener { picker, _, _ ->
                val childCount = picker.childCount
                for (i in 0 until childCount) {
                    val child: View = picker.getChildAt(i)
                    if (child is EditText) {
                        if (picker.value == child.text.toString().toIntOrNull()) {
                            child.setTextColor(Color.WHITE)
                            child.typeface = Typeface.create(typeface, Typeface.BOLD)
                        } else {
                            child.setTextColor(Color.GRAY)
                            child.typeface = typeface
                        }
                    }
                }
            }

            numberPicker.invalidate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SoonBlockedPrivateApi")
    fun NumberPicker.removeSelectionDivider() {
        try {
            // Remove the selection divider drawable
            val dividerField = NumberPicker::class.java.getDeclaredField("mSelectionDivider")
            dividerField.isAccessible = true
            dividerField.set(this, ColorDrawable(Color.TRANSPARENT))

            val heightField = NumberPicker::class.java.getDeclaredField("mSelectionDividerHeight")
            heightField.isAccessible = true
            heightField.setInt(this, 0)

            // Refresh the view
            this.invalidate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
