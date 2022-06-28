package com.mega.carrentalcustomer.repository

import com.mega.carrentalcustomer.network.service.CarRentalService
import com.mega.carrentalcustomer.util.base.BaseRepository
import javax.inject.Inject

class AuthRepository @Inject constructor(private val carRentalService: CarRentalService) : BaseRepository() {

    suspend fun login(
        email : String,
        password : String
    ) = safeApiRequest {
        carRentalService.login(email,password)
    }

    suspend fun register(
        name : String,
        email :String,
        password :String
    ) = safeApiRequest {
        carRentalService.register(name,email,password)
    }
}