package com.example.wonderfulcompose.data.models

import androidx.annotation.IntRange

data class CatPresenter(
    val name: String,
    val avatar: String,
    val createdAt: String,
    val bio: String,
    @IntRange(from = 0, to = 100) val playful: Int = (0..100).random()
)
