package com.example.quotationapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuotesEntity::class], version = 1,exportSchema = false)
abstract class QuotesDatabase: RoomDatabase() {
    abstract fun quotesDao():QuotesDao
}