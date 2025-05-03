package com.example.unihub.data.model.event


import com.google.gson.annotations.SerializedName

data class EventsResponseItem(
    @SerializedName("club")
    val club: Club?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("eventDate")
    val eventDate: String,
    @SerializedName("eventName")
    val eventName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("shortDescription")
    val shortDescription: String
)