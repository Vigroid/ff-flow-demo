package com.farfetch.ffflowdemo.demo.data

data class MainUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val productList: List<ProductUiState> = listOf(),
)