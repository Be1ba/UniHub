package com.example.unihub.data.model.post


import com.google.gson.annotations.SerializedName

data class CreatePostRequest(
    @SerializedName("content")
    val content: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String
)