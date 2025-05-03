package com.example.unihub.data.model.club


import com.google.gson.annotations.SerializedName

data class ClubsResponseItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("goal")
    val goal: String,
    @SerializedName("head")
    val head: Head,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Int
)