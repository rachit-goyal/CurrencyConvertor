package com.learn.currencyconvertor.domain.repositroy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learn.coinswap.data.local.CurrencyRateDao
import com.learn.coinswap.data.local.entity.CurrencyRateEntity
import com.learn.currencyconvertor.data.mapper.toCurrencyRate
import com.learn.currencyconvertor.data.remote.models.CurrencyRate
import com.learn.currencyconvertor.data.remote.models.CurrencyResponse
import com.learn.currencyconvertor.data.remote.models.Rates
import com.learn.currencyconvertor.data.remote.retrofit.CurrencyAPI
import com.learn.currencyconvertor.presentation.getOrAwaitValue
import com.learn.currencyconvertor.presentation.viewModel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * created by Rachit on 2/23/2024.
 */
class CurrencyRepoTest{
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecuterRule = InstantTaskExecutorRule()
    @Mock
    lateinit var repo: CurrencyAPI
    @Mock
    lateinit var dao: CurrencyRateDao
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this )
        Dispatchers.setMain(testDispatcher)
    }

    val resp=CurrencyResponse("","","",Rates(0.0,0.0,0.0,
        0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0
        ,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0
        ,0.0,0.0,0.0),0)
    @Test
    fun getCurrency()= runTest{
       Mockito.`when`(repo.getCurrencyList()).thenReturn(Response.success(resp))
        testDispatcher.scheduler.advanceUntilIdle()
        val result=repo.getCurrencyList()
        assertEquals(result.code(),200)
    }
    val list= listOf(CurrencyRateEntity("","",0.0),CurrencyRateEntity("","",0.0))
    @Test
    fun getCurrencyListFromDao()= runTest{
        Mockito.`when`(dao.getAllCurrencyRates()).thenReturn(list)
        testDispatcher.scheduler.advanceUntilIdle()
        val result=repo.getCurrencyList()
        assertEquals(2,list.size)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}