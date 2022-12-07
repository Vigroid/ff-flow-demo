package com.farfetch.ffflowdemo.demo.data

import androidx.compose.ui.graphics.Color
import com.farfetch.ffflowdemo.demo.ui.LikedProduct
import com.farfetch.ffflowdemo.demo.ui.ProductUiState
import kotlinx.coroutines.flow.map

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

class ProductRepository(
    private val likedDao: LikedProductDao
) {
    val productList = mockProducts(500)

    val likedProductsFlow = likedDao.getAll().map { list -> list.map { it.productId } }

    suspend fun addToLikedProducts(productId: String) {
        likedDao.insertProduct(id = LikedProduct(productId))
    }

    suspend fun removeFromLikeProducts(productId: String) {
        likedDao.deleteProduct(id = LikedProduct(productId))
    }

    suspend fun clearAllLiked() {
        likedDao.deleteAllProducts()
    }

    suspend fun addToLikedProducts(productIds: List<String>) {
        likedDao.insertProducts(ids = productIds.map { LikedProduct(it) })
    }
}