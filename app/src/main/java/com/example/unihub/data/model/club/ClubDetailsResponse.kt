package com.example.unihub.data.model.club


import com.google.gson.annotations.SerializedName

data class ClubDetailsResponse(
    @SerializedName("attractionMethods")
    val attractionMethods: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("financing")
    val financing: String,
    @SerializedName("goal")
    val goal: String,
    @SerializedName("head")
    val head: Head,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("resources")
    val resources: String
)