package com.mega.carrentalcustomer.repository

import com.mega.carrentalcustomer.network.service.CarRentalService
import com.mega.carrentalcustomer.util.base.BaseRepository
import javax.inject.Inject


class RentRepository @Inject constructor(private val carRentalService: CarRentalService ) : BaseRepository() {
    suspend fun rentCar(
        adId : String,
        ownerId : String,
        ownerName : String,
        carName : String,
        email : String,
        dailyFee : String,
        startDate : String,
        endDate : String,
    ) = safeApiRequest {
        carRentalService.postReservation(adId,ownerId,ownerName,carName,email,dailyFee,startDate,endDate)
    }

    suspend fun getReservation(
        email : String
    ) = safeApiRequest {
        carRentalService.getReservation(email)
    }
}