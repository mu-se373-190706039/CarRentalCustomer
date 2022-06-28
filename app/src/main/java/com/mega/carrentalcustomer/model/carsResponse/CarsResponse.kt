package com.mega.carrentalcustomer.model.carsResponse


import com.google.gson.annotations.SerializedName

data class CarsResponse(
    @SerializedName("cars")
    val cars: List<Car>?,
    @SerializedName("success")
    val success: Int?
)