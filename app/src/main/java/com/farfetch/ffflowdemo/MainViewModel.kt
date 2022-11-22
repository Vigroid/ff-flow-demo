package com.farfetch.ffflowdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farfetch.ffflowdemo.demo.data.ProductRepository
import com.farfetch.ffflowdemo.demo.data.MainUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val productRepo: ProductRepository = ProductRepository()
) : ViewModel() {
    val uiState = flow {
        emit(MainUiState(isLoading = true))
        delay(1000)
        emit(MainUiState(productList = productRepo.productList))
    }.catch {
        emit(MainUiState(isError = true))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainUiState(isLoading = true)
    )
}