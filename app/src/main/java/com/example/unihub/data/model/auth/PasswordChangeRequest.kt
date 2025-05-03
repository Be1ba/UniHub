package com.example.unihub.data.model.auth


import com.google.gson.annotations.SerializedName

data class PasswordChangeRequest(
    @SerializedName("currentPassword")
    val currentPassword: String,
    @SerializedName("newPassword")
    val newPassword: String
)