package com.brian.kotlinkoinmvvmcoroutines.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brian.kotlinkoinmvvmcoroutines.model.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
