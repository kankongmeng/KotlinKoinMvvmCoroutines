package com.brian.kotlinkoinmvvmcoroutines.repository

import com.brian.kotlinkoinmvvmcoroutines.api.ApiServices
import com.brian.kotlinkoinmvvmcoroutines.model.Item
import com.brian.kotlinkoinmvvmcoroutines.room.ItemDao

class ItemRepository(private val api: ApiServices, private val itemDao: ItemDao) {
    suspend fun getItemList(): Resource<List<Item>> {
        return try {
            val result = api.getItems().await()
            itemDao.insertAll(result)
            Resource.Success(result)
        } catch (ex: Exception) {
            Resource.Success(itemDao.getAll())
        }
    }
}
