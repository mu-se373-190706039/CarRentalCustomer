package com.mega.carrentalcustomer.model.rentResponse


import com.google.gson.annotations.SerializedName

data class RentHistoryResponse(
    @SerializedName("rent")
    val rent: List<Rent>?,
    @SerializedName("success")
    val success: Int?
)