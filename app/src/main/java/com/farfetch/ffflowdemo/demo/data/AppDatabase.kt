package com.farfetch.ffflowdemo.demo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farfetch.ffflowdemo.demo.ui.LikedProduct

@Database(entities = [LikedProduct::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun likedDao(): LikedProductDao
}