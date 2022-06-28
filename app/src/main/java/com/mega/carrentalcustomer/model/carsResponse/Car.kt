package com.mega.carrentalcustomer.model.carsResponse


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
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
    @SerializedName("fuel")
    val fuel: String?,
    @SerializedName("owner_name")
    val ownerName: String?,
    @SerializedName("owner_id")
    val ownerId: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("time_to_be_added")
    val timeToBeAdded: String?
) : Parcelable