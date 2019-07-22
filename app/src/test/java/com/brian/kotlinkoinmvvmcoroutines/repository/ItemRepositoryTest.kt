package com.brian.kotlinkoinmvvmcoroutines.repository

import com.brian.kotlinkoinmvvmcoroutines.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.net.HttpURLConnection

class ItemRepositoryTest : BaseTest() {

    @Test
    fun `getItems on success`() {
        mockHttpResponse(mockServer, "item_data.json", HttpURLConnection.HTTP_OK)

        val repository = ItemRepository(apiServices)
        val resource = runBlocking { repository.getItemList() }

        assertTrue(resource is Resource.Success)
        val data = (resource as Resource.Success).data
        assertNotNull(data)
        assertEquals("1", data!!.get(0).id)
    }

    @Test
    fun `getItems on failure exception`() {
        mockHttpResponse(mockServer, "item_data.json", HttpURLConnection.HTTP_NOT_FOUND)

        val repository = ItemRepository(apiServices)
        val resource = runBlocking { repository.getItemList() }

        assertTrue(resource is Resource.Error)
        val data = (resource as Resource.Error).exception.message
        assertNotNull(data)
    }
}
