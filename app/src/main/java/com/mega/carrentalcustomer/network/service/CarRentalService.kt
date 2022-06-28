package com.mega.carrentalcustomer.network.service

import com.mega.carrentalcustomer.model.authResponse.AuthResponse
import com.mega.carrentalcustomer.model.carsResponse.CarsResponse
import com.mega.carrentalcustomer.model.rentResponse.RentHistoryResponse
import com.mega.carrentalcustomer.model.rentResponse.RentResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface CarRentalService {

    //Authentication
    @FormUrlEncoded
    @POST("/car_rental/app_users/login.php")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("/car_rental/app_users/register.php")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

    // Rental Details
    @GET("/car_rental/app_users/get_all_cars.php")
    suspend fun fetchAllCars() : CarsResponse

    @FormUrlEncoded
    @POST("/car_rental/app_users/post_rent.php")
    suspend fun postReservation(
        @Field("ad_id") adId : String,
        @Field("owner_id")ownerId : String,
        @Field("owner_name")ownerName : String,
        @Field("car_name")carName : String,
        @Field("email")email : String,
        @Field("daily_fee")dailyFee : String,
        @Field("start_date")startDate : String,
        @Field("end_date")endDate : String,
    ) : RentResponse

    @FormUrlEncoded
    @POST("/car_rental/app_users/get_rent.php")
    suspend fun getReservation(
        @Field("email") email : String
    ) : RentHistoryResponse
}