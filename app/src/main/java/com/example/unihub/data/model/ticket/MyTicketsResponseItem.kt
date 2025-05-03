package com.example.unihub.data.model.ticket


import com.google.gson.annotations.SerializedName

data class MyTicketsResponseItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("numberOfPersons")
    val numberOfPersons: Int,
    @SerializedName("poster")
    val poster: Poster,
    @SerializedName("status")
    val status: String
)