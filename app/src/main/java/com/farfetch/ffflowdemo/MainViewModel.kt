package com.farfetch.ffflowdemo

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farfetch.ffflowdemo.demo.ui.MainUiState
import com.farfetch.ffflowdemo.demo.data.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    val likedProducts = productRepo.likedProductsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )

    // Use flow operator
    val likedTotalCount = productRepo.likedProductsFlow.map {
        it.size
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    fun modifyLikeProducts(isAdd: Boolean, productId: String) {
        if (isAdd) {
            productRepo.addToLikedProducts(productId = productId)
        } else {
            productRepo.removeFromLikeProducts(productId = productId)
        }
    }

    fun clearLikedProducts() = productRepo.clearAllLiked()

    fun likeAllProducts() {
        val allProductIds = productRepo.productList.map { it.productId }
        productRepo.addToLikedProducts(allProductIds)
    }

    fun likeRedProductsOnly() {
        val redProductIds = productRepo.productList
            .filter { it.iconColor == Color.Red }
            .map { it.productId }
        productRepo.clearAllLiked()
        productRepo.addToLikedProducts(redProductIds)
    }
}