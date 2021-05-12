package com.brian.kotlinkoinmvvmcoroutines.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey val id: String,
    val name: String?,
    val imageUrl: String?
)
