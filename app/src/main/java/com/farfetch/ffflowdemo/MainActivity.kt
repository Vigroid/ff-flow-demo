package com.farfetch.ffflowdemo

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.farfetch.ffflowdemo.demo.ui.ProductList
import com.farfetch.ffflowdemo.ui.theme.FarfetchFlowDemoTheme

class MainActivity : ComponentActivity() {
    private val vm by lazy { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarfetchFlowDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel = vm) { isAdd, id ->
                        vm.modifyLikeProducts(isAdd, id)
                        // Need to recompose the UI manually.
                        existingComposeView?.disposeComposition()
                    }
                }
            }
        }
    }

    // Helper function to get activity root compose view
    private val existingComposeView
        get() = window.decorView
            .findViewById<ViewGroup>(android.R.id.content)
            .getChildAt(0) as? ComposeView
}

@Composable
private fun MainScreen(viewModel: MainViewModel, onModifyLikeProducts: (Boolean, String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val likedList = viewModel.likedProducts
    when {
        uiState.isLoading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading....")
        }
        uiState.isError -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Something went wrong")
        }
        else -> ProductList(
            listState = viewModel.lazyState,
            modifier = Modifier.fillMaxSize(),
            products = uiState.productList,
            likedList = likedList,
            onModifyLiked = onModifyLikeProducts
        )
    }
}