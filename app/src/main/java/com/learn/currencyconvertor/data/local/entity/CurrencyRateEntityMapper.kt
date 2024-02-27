package com.learn.coinswap.data.local.entity

import com.learn.currencyconvertor.data.remote.models.CurrencyRate


/**
created by Rachit on 2/21/2024.
 */
fun CurrencyRateEntity.toCurrencyRate(): CurrencyRate {

    return CurrencyRate(
        code, name, rate
    )
}

fun CurrencyRate.toCurrencyRateEntity(): CurrencyRateEntity {

    return CurrencyRateEntity(
        code, name, rate
    )
}