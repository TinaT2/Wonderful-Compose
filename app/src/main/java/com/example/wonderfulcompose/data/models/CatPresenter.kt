package com.example.wonderfulcompose.data.models

import androidx.annotation.IntRange
import androidx.compose.ui.graphics.Color

data class CatPresenter(
    override val title: String,
    override val avatar: String,
    val age: String,
    val breed: String,
    val gender: String,
    val bio: String,
    val createdAt: String,
    @IntRange(from = 0, to = 100) val playful: Int = (0..100).random(),
    val colorId: Int,
    override val color: Color? = null,
    override val id:Int
) : ListItem
