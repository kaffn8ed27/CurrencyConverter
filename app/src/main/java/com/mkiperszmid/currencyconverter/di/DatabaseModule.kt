package com.mkiperszmid.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.mkiperszmid.currencyconverter.db.AppDatabase
import com.mkiperszmid.currencyconverter.db.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "currency-converter.db").build()

    @Provides
    @Singleton
    fun provideCurrencyDao(db: AppDatabase): CurrencyDao = db.currencyDao()
}