package com.example.unihub.data.model.event


import com.google.gson.annotations.SerializedName

data class Head(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String
)