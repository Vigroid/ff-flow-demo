package com.farfetch.ffflowdemo.demo.ui

data class MainUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val productList: List<ProductUiState> = listOf(),
)