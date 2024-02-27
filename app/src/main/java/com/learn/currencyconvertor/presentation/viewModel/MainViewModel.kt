package com.learn.currencyconvertor.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.currencyconvertor.data.remote.models.CurrencyRate
import com.learn.currencyconvertor.domain.repositroy.CurrencyRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
created by Rachit on 2/21/2024.
 */

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: CurrencyRepo) : ViewModel() {

    val productsLiveData = MutableLiveData<List<CurrencyRate>?>()


    init {

        getData()

    }

     fun getData() {
        viewModelScope.launch {
            val response = repo.getData()
            if (response != null) {
                productsLiveData.postValue(response)
            }
        }    }
}