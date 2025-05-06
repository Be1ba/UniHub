package com.example.unihub.data.model.admin


import com.google.gson.annotations.SerializedName

data class CreateEventAdminResponseItem(
    @SerializedName("club")
    val club: Club?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("eventDate")
    val eventDate: String?,
    @SerializedName("eventName")
    val eventName: String?,
    @SerializedName("head")
    val head: Head?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("status")
    val status: String?
)