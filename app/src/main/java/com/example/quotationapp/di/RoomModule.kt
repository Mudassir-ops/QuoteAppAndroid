package com.example.quotationapp.di

import android.content.Context
import androidx.room.Room
import com.example.quotationapp.data.QuotesDao
import com.example.quotationapp.data.QuotesDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideQuotesDatabase(@ApplicationContext context: Context): QuotesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            QuotesDatabase::class.java,
            "quotes_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideUpdatedAppDao(database: QuotesDatabase): QuotesDao {
        return database.quotesDao()
    }

    @Provides
    @Singleton
    fun provideFirestoreDatabase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}