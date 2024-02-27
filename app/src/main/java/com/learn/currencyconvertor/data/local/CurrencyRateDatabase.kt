package com.learn.coinswap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learn.coinswap.data.local.entity.CurrencyRateEntity

/**
created by Rachit on 2/21/2024.
 */

@Database(entities = [CurrencyRateEntity::class], version = 1)
abstract class CurrencyRateDatabase : RoomDatabase() {
    abstract val currencyRateDao: CurrencyRateDao
}