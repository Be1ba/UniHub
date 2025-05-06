package com.example.unihub.data.model.admin


import com.google.gson.annotations.SerializedName

data class CreateEventAdminDetailsResponse(
    @SerializedName("clubHead")
    val clubHead: String?,
    @SerializedName("clubId")
    val clubId: Int?,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("eventDate")
    val eventDate: String?,
    @SerializedName("eventName")
    val eventName: String?,
    @SerializedName("goal")
    val goal: String?,
    @SerializedName("headId")
    val headId: Int?,
    @SerializedName("id")
    val id: Int?,
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
    val sponsorship: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)