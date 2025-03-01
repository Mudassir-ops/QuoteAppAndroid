package com.example.quotationapp.json

import com.example.quotationapp.R
import com.google.gson.annotations.SerializedName


data class Categories(
    @SerializedName("category_name") var categoryName: String? = null,
    @SerializedName("quotes") var quotes: ArrayList<Quotes>? = null,
    var imageResource: Int = R.drawable.app_icon
)