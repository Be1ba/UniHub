package com.example.unihub.data.model.admin


import com.google.gson.annotations.SerializedName

data class UsersListResponseItem(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("surname")
    val surname: String?
)