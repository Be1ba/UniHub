package com.example.unihub.data.model.post


import com.google.gson.annotations.SerializedName

data class PostsResponseItem(
    @SerializedName("club")
    val club: Club?,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("user")
    val user: User
)