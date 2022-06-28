package com.mega.carrentalcustomer.model.authResponse


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)