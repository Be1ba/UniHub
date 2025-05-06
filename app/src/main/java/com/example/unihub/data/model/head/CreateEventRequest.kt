package com.example.unihub.data.model.head


import com.google.gson.annotations.SerializedName

data class CreateEventRequest(
    @SerializedName("clubHead")
    val clubHead: String?,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("eventDate")
    val eventDate: String?,
    @SerializedName("eventName")
    val eventName: String?,
    @SerializedName("goal")
    val goal: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("organizers")
    val organizers: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("schedule")
    val schedule: String?,
    @SerializedName("shortDescription")
    val shortDescription: String?,
    @SerializedName("sponsorship")
    val sponsorship: String?
)