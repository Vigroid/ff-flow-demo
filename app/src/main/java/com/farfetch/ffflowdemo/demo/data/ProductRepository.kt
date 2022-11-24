package com.farfetch.ffflowdemo.demo.data

import androidx.compose.ui.graphics.Color
import com.farfetch.ffflowdemo.demo.ui.ProductUiState
import kotlinx.coroutines.flow.MutableStateFlow

val demoColorList = listOf(
    Color.Black,
    Color.Gray,
    Color.White,
    Color.Red,
    Color.Green,
    Color.Yellow,
    Color.Cyan,
)

private val demoBrands = listOf(
    "Gucci",
    "Balenciaga",
    "Off-White",
    "Alexander Wang",
    "Maison Margiela",
    "GANNI",
    "UGG",
)

fun mockProducts(count: Int) = List(count) {
    ProductUiState(
        productId = "Pid $it",
        iconColor = demoColorList[it % demoColorList.size],
        brand = demoBrands[it % demoBrands.size],
        shortDesc = "Short desc $it"
    )
}

class ProductRepository {
    val productList = mockProducts(500)

    private val _likedProductsFlow = MutableStateFlow(listOf<String>())
    val likedProductsFlow = _likedProductsFlow

    fun addToLikedProducts(productId: String) {
        _likedProductsFlow.value = _likedProductsFlow.value + productId
    }

    fun removeFromLikeProducts(productId: String) {
        _likedProductsFlow.value = _likedProductsFlow.value - productId
    }

    fun clearAllLiked() {
        _likedProductsFlow.value = emptyList()
    }

    fun addToLikedProducts(productIds: List<String>) {
        _likedProductsFlow.value = productIds
    }
}