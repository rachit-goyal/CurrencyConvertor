package com.learn.currencyconvertor

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.learn.coinswap.data.local.CurrencyRateDao
import com.learn.coinswap.data.local.CurrencyRateDatabase
import com.learn.coinswap.data.local.entity.CurrencyRateEntity
import com.learn.currencyconvertor.data.remote.models.CurrencyRate
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
created by Rachit on 2/22/2024.
 */
class CurrencyDaoTest {


    lateinit var database: CurrencyRateDatabase
    lateinit var dao: CurrencyRateDao

    @Before
    fun setUp() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CurrencyRateDatabase::class.java
        ).allowMainThreadQueries().build()
        dao=database.currencyRateDao
    }

    @Test
    fun insert_currency_list_expecting_list()= runBlocking {
        val curr1=CurrencyRateEntity("IDR", "Indonesian Rupiah", 1.0)
        val curr2=CurrencyRateEntity("ILS", "Israeli New Sheqel", 2.0)
        val curr3=CurrencyRateEntity("KRW", "South Korean Won", 3.0)
        val list=listOf(curr1,curr2,curr3)
        dao.upsertAll(list)
        val result=dao.getAllCurrencyRates()
        Assert.assertEquals(3,result.size)
        Assert.assertEquals("IDR", result[0].code)
    }



    @After
    fun tearDown() {
    database.close()

    }
}