package com.example.unihub.data.model.head


import com.google.gson.annotations.SerializedName

data class HeadProfileResponse(
    @SerializedName("birthDate")
    val birthDate: String?,
    @SerializedName("clubInfo")
    val clubInfo: ClubInfo?,
    @SerializedName("clubName")
    val clubName: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("surname")
    val surname: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)