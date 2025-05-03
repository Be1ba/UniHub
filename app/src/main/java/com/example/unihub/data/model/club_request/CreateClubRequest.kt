package com.example.unihub.data.model.club_request


import com.google.gson.annotations.SerializedName

data class CreateClubRequest(
    @SerializedName("attractionMethods")
    val attractionMethods: String,
    @SerializedName("clubName")
    val clubName: String,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("communication")
    val communication: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("financing")
    val financing: String,
    @SerializedName("goal")
    val goal: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("resources")
    val resources: String,
    @SerializedName("title")
    val title: String
)