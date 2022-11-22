package com.farfetch.ffflowdemo.demo.data

import androidx.compose.ui.graphics.Color

data class ProductUiState(
    val productId: String,
    val iconColor: Color,
    val brand: String,
    val shortDesc: String,
)
