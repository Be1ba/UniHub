package com.example.unihub.data.model.users


import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("birthDate")
    val birthDate: Any?,
    @SerializedName("clubName")
    val clubName: Any?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: Any?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: Any?,
    @SerializedName("role")
    val role: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)