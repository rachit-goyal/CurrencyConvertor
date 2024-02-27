package com.learn.coinswap.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
created by Rachit on 2/21/2024.
 */

@Entity
data class CurrencyRateEntity (
    @PrimaryKey
    val code:String,
    val name:String,
    val rate:Double
)