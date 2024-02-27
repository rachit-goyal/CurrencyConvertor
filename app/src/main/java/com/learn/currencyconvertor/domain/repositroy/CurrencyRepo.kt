package com.learn.currencyconvertor.domain.repositroy

import com.learn.coinswap.data.local.CurrencyRateDao
import com.learn.coinswap.data.local.entity.toCurrencyRate
import com.learn.coinswap.data.local.entity.toCurrencyRateEntity
import com.learn.currencyconvertor.data.mapper.toCurrencyRate
import com.learn.currencyconvertor.data.remote.models.CurrencyRate
import com.learn.currencyconvertor.data.remote.models.CurrencyResponse
import com.learn.currencyconvertor.data.remote.retrofit.CurrencyAPI
import retrofit2.Response
import javax.inject.Inject

/**
created by Rachit on 2/21/2024.
 */
class CurrencyRepo @Inject constructor(
    private val currencyAPI: CurrencyAPI,
    private val currencyRateDao: CurrencyRateDao,
) {


    suspend fun getData(): List<CurrencyRate>? {
        return if(currencyRateDao.getAllCurrencyRates().isEmpty().not()){
            currencyRateDao.getAllCurrencyRates().map {
                it.toCurrencyRate()
            }
        } else{
            try {
                val items = currencyAPI.getCurrencyList().body()?.toCurrencyRate()
                items?.let {
                    currencyRateDao.upsertAll(items.map {
                        it.toCurrencyRateEntity()
                    })
                }
                items
            }
            catch (e:Exception){
                return null
            }

        }

    }

    suspend fun getCurrencyDataFromBackGround(): List<CurrencyRate>? {
        val items = currencyAPI.getCurrencyList().body()?.toCurrencyRate()
        items?.let {
            currencyRateDao.upsertAll(items.map {
                it.toCurrencyRateEntity()
            })
        }
        return items
    }


}