package com.learn.currencyconvertor.di

import android.app.Application
import androidx.room.Room
import com.learn.coinswap.data.local.CurrencyRateDao
import com.learn.coinswap.data.local.CurrencyRateDatabase
import com.learn.currencyconvertor.data.remote.retrofit.CurrencyAPI
import com.learn.currencyconvertor.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
created by Rachit on 2/21/2024.
 */
@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesFakerAPI(retrofit: Retrofit) : CurrencyAPI {
        return retrofit.create(CurrencyAPI::class.java)
    }
    @Provides
    @Singleton
    fun provideDatabase(application: Application): CurrencyRateDatabase {
        return Room
            .databaseBuilder(
                application,
                CurrencyRateDatabase::class.java,
                "currency_db"
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(currencyRateDatabase: CurrencyRateDatabase): CurrencyRateDao {
        return currencyRateDatabase.currencyRateDao
    }
}