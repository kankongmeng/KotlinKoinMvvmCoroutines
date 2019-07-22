package com.brian.kotlinkoinmvvmcoroutines.repository

import com.brian.kotlinkoinmvvmcoroutines.api.ApiServices
import com.brian.kotlinkoinmvvmcoroutines.model.Item

class ItemRepository(private val api: ApiServices) {
    suspend fun getItemList(): Resource<List<Item>> {
        return try {
            val result = api.getItems().await()
            Resource.Success(result)
        } catch (ex: Exception) {
            Resource.Error(ex)
        }
    }
}
