package com.example.unihub.data.model.ticket


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("eventDate")
    val eventDate: String,
    @SerializedName("eventTitle")
    val eventTitle: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("time")
    val time: String
)