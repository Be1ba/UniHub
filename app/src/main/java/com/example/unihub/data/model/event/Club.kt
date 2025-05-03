package com.example.unihub.data.model.event


import com.google.gson.annotations.SerializedName

data class Club(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)