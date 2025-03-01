package com.example.quotationapp.json

import com.google.gson.annotations.SerializedName

data class QuotesBaseClass(@SerializedName("quotes_data") var quotesData: QuotesData? = null, )
