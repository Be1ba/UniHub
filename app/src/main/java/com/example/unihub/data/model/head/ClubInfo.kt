package com.example.unihub.data.model.head


import com.google.gson.annotations.SerializedName

data class ClubInfo(
    @SerializedName("description")
    val description: String?,
    @SerializedName("goal")
    val goal: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)