package com.farfetch.ffflowdemo.demo.ui

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

data class ProductUiState(
    val productId: String,
    val iconColor: Color,
    val brand: String,
    val shortDesc: String,
)

@Entity
data class LikedProduct(
    @PrimaryKey val productId: String
)