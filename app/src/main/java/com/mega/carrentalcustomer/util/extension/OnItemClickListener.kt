package com.mega.carrentalcustomer.util.extension

import com.mega.carrentalcustomer.model.carsResponse.Car


interface OnItemClickListener {
    fun onClick(car : Car)
}