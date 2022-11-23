package com.farfetch.ffflowdemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.farfetch.ffflowdemo.demo.ui.ProductList
import com.farfetch.ffflowdemo.ui.theme.FarfetchFlowDemoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val vm by lazy { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainContent = findViewById<ComposeView>(R.id.compose_view)
        mainContent.setContent {
            FarfetchFlowDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel = vm) { isAdd, id ->
                        vm.modifyLikeProducts(isAdd = isAdd, productId = id)
                    }
                }
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.likedTotalCount.collect { size ->
                    updateViews(size)
                }
            }
        }
        // Clear all products to liked list
        findViewById<Button>(R.id.btn_clear_all).setOnClickListener {
            vm.clearLikedProducts()
        }
        // Add all products to liked list
        findViewById<Button>(R.id.btn_add_all).setOnClickListener {
            vm.likeAllProducts()
        }
        // Add all red products to liked list
        findViewById<Button>(R.id.btn_add_red).setOnClickListener {
            vm.likeRedProductsOnly()
        }
    }

    // This function is called multiple times
    // and the complexity of it will be increased if new features are added.
    private fun updateViews(count: Int) {
        val tvCount = findViewById<TextView>(R.id.tv_count)
        tvCount.text = if (count == 0) {
            getString(R.string.liked_items_empty)
        } else {
            getString(R.string.liked_items_text, count)
        }
    }
}

@Composable
private fun MainScreen(viewModel: MainViewModel, onModifyLiked: (Boolean, String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
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
            modifier = Modifier.fillMaxSize(),
            products = uiState.productList,
            likedList = viewModel.likedProducts,
            onModifyLiked = onModifyLiked
        )
    }
}