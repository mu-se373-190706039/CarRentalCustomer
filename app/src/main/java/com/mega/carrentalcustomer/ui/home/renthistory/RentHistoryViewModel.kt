package com.mega.carrentalcustomer.ui.home.renthistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mega.carrentalcustomer.model.rentResponse.RentHistoryResponse
import com.mega.carrentalcustomer.repository.RentRepository
import com.mega.carrentalcustomer.util.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentHistoryViewModel @Inject constructor(private val rentRepository: RentRepository) : ViewModel() {
    var rentHistoryResponse : MutableLiveData<RentHistoryResponse>? = MutableLiveData()
    val isLoading : MutableLiveData<Boolean> = MutableLiveData()
    val onError : MutableLiveData<String?> = MutableLiveData()

    fun getReservation(
        email : String
    ) = viewModelScope.launch {
        isLoading.value = true
        val request = rentRepository.getReservation(email)

        when(request){
            is NetworkResult.Success ->{
                isLoading.value = false
                rentHistoryResponse?.value = request.data!!
            }
            is NetworkResult.Error ->{
                isLoading.value = false
                onError.value = request.message
            }
        }
    }
}