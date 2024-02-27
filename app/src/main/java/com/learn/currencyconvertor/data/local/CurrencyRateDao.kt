package com.learn.coinswap.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.learn.coinswap.data.local.entity.CurrencyRateEntity

/**
created by Rachit on 2/21/2024.
 */

@Dao
interface CurrencyRateDao {

    @Upsert
    suspend fun upsertAll(currencyRates: List<CurrencyRateEntity>)

    @Query("SELECT * FROM currencyrateentity")
    suspend fun getAllCurrencyRates(): List<CurrencyRateEntity>
}