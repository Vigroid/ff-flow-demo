@file:OptIn(ExperimentalAnimationApi::class)

package com.farfetch.ffflowdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.farfetch.ffflowdemo.demo.data.AppDatabase
import com.farfetch.ffflowdemo.demo.data.ProductRepository
import com.farfetch.ffflowdemo.demo.ui.AdvertisementUIState
import com.farfetch.ffflowdemo.demo.ui.ProductList
import com.farfetch.ffflowdemo.ui.theme.FarfetchFlowDemoTheme

class MainActivity : ComponentActivity() {
    // Just for demo. We should initialize db in Application
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    private val vm by lazy { MainViewModel(productRepo = ProductRepository(db.likedDao())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarfetchFlowDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel = vm)
                }
            }
        }
    }
}

@Composable
private fun MainScreen(viewModel: MainViewModel) {
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
        else -> Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ProductList(
                modifier = Modifier.weight(1f),
                products = uiState.productList,
                likedList = viewModel.likedProducts,
                onModifyLiked = viewModel::modifyLikeProducts
            )
            val count by viewModel.likedTotalCount.collectAsState()
            val adInfo by viewModel.advertisementUiState.collectAsState()
            BottomBar(
                totalCount = count,
                advertisementUIState = adInfo,
                onClickClear = viewModel::clearLikedProducts,
                onClickLikeAll = viewModel::likeAllProducts,
                onClickLikeRedOnly = viewModel::likeRedProductsOnly,
            )
        }
    }
}

@Composable
private fun BottomBar(
    totalCount: Int,
    advertisementUIState: AdvertisementUIState?,
    onClickClear: () -> Unit,
    onClickLikeAll: () -> Unit,
    onClickLikeRedOnly: () -> Unit,
) {
    val displayText = if (totalCount == 0) {
        stringResource(R.string.liked_items_empty)
    } else {
        stringResource(R.string.liked_items_text, totalCount)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(thickness = 3.dp, color = Color.Black)
        AnimatedContent(
            targetState = advertisementUIState,
            transitionSpec = {
                slideInVertically(tween(500)) { height -> height } with
                        slideOutVertically(tween(500)) { height -> -height }
            }) {
            it?.let { info ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = info.title, modifier = Modifier
                            .weight(2f)
                            .padding(horizontal = 12.dp)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(info.icon)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(colorResource(id = R.color.teal_200))
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = displayText,
                modifier = Modifier
                    .weight(1f),
                fontSize = 20.sp
            )
            Button(contentPadding = PaddingValues(0.dp), onClick = onClickClear) {
                Text(
                    text = stringResource(id = R.string.clear_all_liked_items),
                    fontSize = 12.sp
                )
            }
            Button(contentPadding = PaddingValues(0.dp), onClick = onClickLikeAll) {
                Text(
                    text = stringResource(id = R.string.add_all_liked_items),
                    fontSize = 12.sp
                )
            }
            Button(contentPadding = PaddingValues(0.dp), onClick = onClickLikeRedOnly) {
                Text(
                    text = stringResource(id = R.string.like_red_items),
                    fontSize = 12.sp
                )
            }
        }
    }
}