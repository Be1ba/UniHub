package com.example.unihub.data.model.poster


import com.google.gson.annotations.SerializedName

data class PosterDetailsResponse(
    @SerializedName("club")
    val club: Club?,
    @SerializedName("description")
    val description: String,
    @SerializedName("eventDate")
    val eventDate: String,
    @SerializedName("eventTitle")
    val eventTitle: String,
    @SerializedName("head")
    val head: Head,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("seats")
    val seats: Int,
    @SerializedName("seatsLeft")
    val seatsLeft: Int,
    @SerializedName("time")
    val time: String
)