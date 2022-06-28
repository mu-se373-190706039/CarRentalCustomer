package com.mega.carrentalcustomer.repository

import com.mega.carrentalcustomer.network.service.CarRentalService
import com.mega.carrentalcustomer.util.base.BaseRepository
import javax.inject.Inject

class CarsRepository @Inject constructor(private val carRentalService: CarRentalService) : BaseRepository() {

    suspend fun fetchAllCars() = safeApiRequest {
        carRentalService.fetchAllCars()
    }
}