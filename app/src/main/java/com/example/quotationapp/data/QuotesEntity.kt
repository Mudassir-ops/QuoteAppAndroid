package com.example.quotationapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_table")
data class QuotesEntity(
    @PrimaryKey(autoGenerate = false)
    val quotesText:String = "",
    val authorName :String = "",
    val isFavourite: Boolean = false
)