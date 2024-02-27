package com.learn.currencyconvertor.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learn.currencyconvertor.domain.repositroy.CurrencyRepo
import com.learn.currencyconvertor.presentation.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * created by Rachit on 2/23/2024.
 */
class MainViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecuterRule = InstantTaskExecutorRule()
    @Mock
    lateinit var repo: CurrencyRepo
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this )
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getCurrency()= runTest{
        Mockito.`when`(repo.getData()).thenReturn(emptyList())
        val sut=MainViewModel(repo)
        sut.getData()
        testDispatcher.scheduler.advanceUntilIdle()
        val result=sut.productsLiveData.getOrAwaitValue()
        Assert.assertEquals(0,result?.size)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}