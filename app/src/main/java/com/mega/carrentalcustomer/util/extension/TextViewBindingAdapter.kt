package com.mega.carrentalcustomer.util.extension

import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 *Created by Mert Melih Aytemur on 5.05.2022.
 */
@BindingAdapter("setRentDeposit")
fun setRentDeposit(textView: TextView, deposit : String?){
    deposit?.let {
        textView.text = "Deposit: $deposit"
    }
}

@BindingAdapter("setDailyFee")
fun setDailyFee(textView: TextView, dailyFee : String?){
    dailyFee?.let {
        textView.text = "Daily Fee: $dailyFee"
    }
}

