package com.example.wonderfulcompose.data.models

import androidx.compose.ui.graphics.Color


interface ListItem {
    val id: Int?
    val color: Color?
    val avatar: String?
    val title: String
}

data class CategoryPresenter(
    override val id: Int,
    override val color: Color,
    override val title: String,
    override val avatar:String? = null
) : ListItem