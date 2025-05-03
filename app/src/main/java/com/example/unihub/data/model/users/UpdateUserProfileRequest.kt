package com.example.unihub.data.model.users


import com.google.gson.annotations.SerializedName

data class UpdateUserProfileRequest(
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("clubName")
    val clubName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("surname")
    val surname: String
)