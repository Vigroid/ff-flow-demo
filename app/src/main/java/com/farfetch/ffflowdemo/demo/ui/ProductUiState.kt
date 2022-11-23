package com.farfetch.ffflowdemo.demo.ui

import androidx.compose.ui.graphics.Color

data class ProductUiState(
    val productId: String,
    val iconColor: Color,
    val brand: String,
    val shortDesc: String,
)
