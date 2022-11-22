package com.farfetch.ffflowdemo.demo.data

import androidx.compose.ui.graphics.Color

private val demoColorList = listOf(
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
}