package com.brian.kotlinkoinmvvmcoroutines.module

import androidx.room.Room
import com.brian.kotlinkoinmvvmcoroutines.repository.ItemRepository
import com.brian.kotlinkoinmvvmcoroutines.room.AppDatabase
import com.brian.kotlinkoinmvvmcoroutines.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModules = module {
    // Provide an instance of room database
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "kotlin-koin-mvvm-coroutines"
        ).build()
    }

    // Provide an instance for item data access object
    single(named("ITEMDAO")) { get<AppDatabase>().itemDao() }

    // Provide an instance of ItemRepository
    factory { ItemRepository(get(), get(named("ITEMDAO"))) }

    // Provide an instance of ViewModel
    viewModel { MainViewModel(get()) }
}
