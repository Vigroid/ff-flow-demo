package com.farfetch.ffflowdemo.demo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farfetch.ffflowdemo.demo.ui.LikedProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedProductDao {
    @Query("SELECT * FROM LikedProduct")
    fun getAll(): Flow<List<LikedProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(id: LikedProduct)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(ids: List<LikedProduct>)

    @Delete
    suspend fun deleteProduct(id: LikedProduct)

    @Query("DELETE FROM LikedProduct")
    suspend fun deleteAllProducts()
}