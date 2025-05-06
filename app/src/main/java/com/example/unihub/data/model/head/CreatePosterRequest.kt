package com.example.unihub.data.model.head


import com.google.gson.annotations.SerializedName

data class CreatePosterRequest(
    @SerializedName("description")
    val description: String?,
    @SerializedName("eventDate")
    val eventDate: String?,
    @SerializedName("eventId")
    val eventId: Int?,
    @SerializedName("eventTitle")
    val eventTitle: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("seats")
    val seats: Int?,
    @SerializedName("time")
    val time: String?
)