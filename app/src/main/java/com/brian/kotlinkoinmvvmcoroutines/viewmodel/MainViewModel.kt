package com.brian.kotlinkoinmvvmcoroutines.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brian.kotlinkoinmvvmcoroutines.core.Event
import com.brian.kotlinkoinmvvmcoroutines.model.Item
import com.brian.kotlinkoinmvvmcoroutines.repository.ItemRepository
import com.brian.kotlinkoinmvvmcoroutines.repository.Resource

class MainViewModel(private val itemRepository: ItemRepository) : BaseViewModel() {
    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> get() = _itemList

    val _itemListErrorEvent = MutableLiveData<Event<String>>()
    val itemListErrorEvent: LiveData<Event<String>> get() = _itemListErrorEvent

    fun getItems() {
        onIO {
            when (val result = itemRepository.getItemList()) {
                is Resource.Success -> _itemList.postValue(result.data)
                is Resource.Error -> result.exception.message?.let {
                    _itemListErrorEvent.postValue(Event(it))
                }
            }
        }
    }
}
