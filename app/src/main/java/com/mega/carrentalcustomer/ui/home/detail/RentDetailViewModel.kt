package com.mega.carrentalcustomer.ui.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mega.carrentalcustomer.model.rentResponse.RentResponse
import com.mega.carrentalcustomer.repository.RentRepository
import com.mega.carrentalcustomer.util.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentDetailViewModel @Inject constructor(private val rentRepository: RentRepository): ViewModel() {
    var rentResponse : MutableLiveData<RentResponse>? = MutableLiveData()
    val onError : MutableLiveData<String?> = MutableLiveData()

    fun rentCar(
        adId : String,
        ownerId : String,
        ownerName : String,
        carName : String,
        email : String,
        dailyFee : String,
        startDate : String,
        endDate : String,
    ) = viewModelScope.launch {
        val request = rentRepository.rentCar(adId,ownerId,ownerName,carName,email,dailyFee,startDate,endDate)

        when(request){
            is NetworkResult.Success ->{
                rentResponse?.value = request.data!!
            }
            is NetworkResult.Error ->{
                onError.value = request.message
            }
        }
    }
}