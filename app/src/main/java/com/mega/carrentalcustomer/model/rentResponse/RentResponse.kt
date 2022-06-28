package com.mega.carrentalcustomer.model.rentResponse


import com.google.gson.annotations.SerializedName

data class RentResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)