package com.example.quotationapp.json

import com.example.quotationapp.json.Categories
import com.google.gson.annotations.SerializedName


data class QuotesData(@SerializedName("categories") var categories: ArrayList<Categories> = arrayListOf())