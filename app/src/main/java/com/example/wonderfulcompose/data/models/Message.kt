package com.example.wonderfulcompose.data.models

data class MessagePresenter(
    val body: String,
    val userName: String,
    val createdAt: String,
    val userId: Int,
    val repliedMessageBody: String? = null,
    val repliedMessageUserName: String? = null,
)

fun MessagePresenter.isMessageMine() = this.userId == 1