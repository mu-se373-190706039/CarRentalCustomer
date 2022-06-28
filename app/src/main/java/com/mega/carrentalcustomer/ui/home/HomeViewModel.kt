package com.mega.carrentalcustomer.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mega.carrentalcustomer.model.carsResponse.CarsResponse
import com.mega.carrentalcustomer.repository.CarsRepository
import com.mega.carrentalcustomer.util.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val carsRepository: CarsRepository) : ViewModel() {
    var carsResponse : MutableLiveData<CarsResponse>? = MutableLiveData()
    val isLoading : MutableLiveData<Boolean> = MutableLiveData()
    val onError : MutableLiveData<String?> = MutableLiveData()

    fun fetchAllCars() = viewModelScope.launch {
        isLoading.value = true
        val request = carsRepository.fetchAllCars()

        when(request){
            is NetworkResult.Success<*> ->{
                isLoading.value = false
                carsResponse?.value = request.data!!
            }
            is NetworkResult.Error<*> ->{
                isLoading.value = false
                onError.value = request.message
            }
        }
    }

}