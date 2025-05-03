package com.example.unihub.data.model.club_request


import com.google.gson.annotations.SerializedName

data class MyCreateClubResponseItem(
    @SerializedName("attractionMethods")
    val attractionMethods: String,
    @SerializedName("clubName")
    val clubName: String,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("communication")
    val communication: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("financing")
    val financing: String,
    @SerializedName("goal")
    val goal: String,
    @SerializedName("headId")
    val headId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("resources")
    val resources: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)