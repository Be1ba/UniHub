package com.example.unihub.data.model.event


import com.google.gson.annotations.SerializedName

data class EventDetailsResponse(
    @SerializedName("club")
    val club: Club?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("eventDate")
    val eventDate: String,
    @SerializedName("eventName")
    val eventName: String,
    @SerializedName("goal")
    val goal: String,
    @SerializedName("head")
    val head: Head,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("organizers")
    val organizers: String,
    @SerializedName("schedule")
    val schedule: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("sponsorship")
    val sponsorship: String
)