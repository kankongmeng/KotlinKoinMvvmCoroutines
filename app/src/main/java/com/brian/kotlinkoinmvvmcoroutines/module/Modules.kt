package com.brian.kotlinkoinmvvmcoroutines.module

import com.brian.kotlinkoinmvvmcoroutines.repository.ItemRepository
import com.brian.kotlinkoinmvvmcoroutines.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    // Provide an instance of ItemRepository
    factory { ItemRepository(get()) }

    // Provide an instance of ViewModel
    viewModel { MainViewModel(get()) }
}
