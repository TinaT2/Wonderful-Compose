package com.example.wonderfulcompose.data.models

import androidx.annotation.IntRange

data class CatPresenter(
    val name: String,
    val avatar: String,
    val age: String,
    val breed: String,
    val gender: String,
    val bio: String,
    val createdAt: String,
    @IntRange(from = 0, to = 100) val playful: Int = (0..100).random()
)
