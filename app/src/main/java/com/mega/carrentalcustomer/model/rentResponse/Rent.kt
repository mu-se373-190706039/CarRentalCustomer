package com.mega.carrentalcustomer.model.rentResponse


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rent(
    @SerializedName("ad_id")
    val adId: String?,
    @SerializedName("car_name")
    val carName: String?,
    @SerializedName("car_photo")
    val carPhoto: String?,
    @SerializedName("daily_fee")
    val dailyFee: String?,
    @SerializedName("deposit")
    val deposit: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("fuel")
    val fuel: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("owner_name")
    val ownerName: String?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("state")
    val state: String?
): Parcelable