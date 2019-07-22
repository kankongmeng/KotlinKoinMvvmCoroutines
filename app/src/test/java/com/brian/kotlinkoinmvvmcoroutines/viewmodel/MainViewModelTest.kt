package com.brian.kotlinkoinmvvmcoroutines.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.brian.kotlinkoinmvvmcoroutines.core.Event
import com.brian.kotlinkoinmvvmcoroutines.model.Item
import com.brian.kotlinkoinmvvmcoroutines.repository.ItemRepository
import com.brian.kotlinkoinmvvmcoroutines.repository.Resource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    private lateinit var itemRepository: ItemRepository
    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        itemRepository = mockk()
    }

    @Test
    fun `getItem when success`() {
        val observer = mockk<Observer<List<Item>>>()
        val result = listOf(Item("1", "a", "a"))
        coEvery { itemRepository.getItemList() } returns Resource.Success(result)

        mainViewModel = MainViewModel(itemRepository)
        mainViewModel.itemList.observeForever(observer)
        mainViewModel.getItems()

        verify {
            observer.onChanged(result)
        }

        Assert.assertEquals("1", mainViewModel.itemList.value?.first()?.id)
    }

    @Test
    fun `getItem when error`() {
        val observer = mockk<Observer<Event<String>>>()
        coEvery { itemRepository.getItemList() } returns Resource.Error(Throwable("error"))

        mainViewModel = MainViewModel(itemRepository)
        mainViewModel.itemListErrorEvent.observeForever(observer)
        mainViewModel.getItems()

        verify {
            observer.onChanged(any())
        }

        Assert.assertEquals("error", mainViewModel.itemListErrorEvent.value?.getContentIfNotHandled())
    }
}
