package com.farfetch.ffflowdemo.demo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farfetch.ffflowdemo.R
import com.farfetch.ffflowdemo.demo.data.ProductUiState
import com.farfetch.ffflowdemo.demo.data.mockProducts
import com.farfetch.ffflowdemo.ui.theme.Shapes

@Composable
fun ProductList(modifier: Modifier, products: List<ProductUiState>) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        items(products) {
            ProductCell(product = it)
        }
    }
}

@Composable
fun ProductCell(product: ProductUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(product.iconColor, shape = Shapes.small)
        )
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(text = product.brand)
            Text(text = product.shortDesc)
        }
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_heart_default),
            contentDescription = null,
        )
    }
}

private val mockProductList = mockProducts(100)

@Preview(showBackground = true)
@Composable
private fun ProductListingPreview() {
    ProductList(modifier = Modifier.fillMaxWidth(), products = mockProductList)
}