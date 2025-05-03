package com.example.unihub.data.model.ticket


import com.google.gson.annotations.SerializedName

data class BookTicketRequest(
    @SerializedName("numberOfPersons")
    val numberOfPersons: Int,
    @SerializedName("paymentProof")
    val paymentProof: String,
    @SerializedName("posterId")
    val posterId: Int
)