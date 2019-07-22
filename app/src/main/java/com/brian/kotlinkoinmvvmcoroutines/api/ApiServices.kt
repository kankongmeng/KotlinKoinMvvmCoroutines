package com.brian.kotlinkoinmvvmcoroutines.api

import com.brian.kotlinkoinmvvmcoroutines.model.Item
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiServices {
    @GET("mainmenu")
    fun getItems(): Deferred<List<Item>>
}
