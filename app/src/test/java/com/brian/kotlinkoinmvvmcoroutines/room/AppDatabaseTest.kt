package com.brian.kotlinkoinmvvmcoroutines.room

import com.brian.kotlinkoinmvvmcoroutines.BaseTest
import org.junit.Assert
import org.junit.Test

class AppDatabaseTest : BaseTest() {

    @Test
    fun testItemDaoInsertAndGetAll() {
        itemDao.insertAll(mockItems);
        itemDao.getAll()

        Assert.assertEquals(itemDao.getAll(), mockItems)
    }
}
