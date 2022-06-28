package com.mega.carrentalcustomer.util.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mega.carrentalcustomer.util.extension.loadImageView

class ImageViewBindingAdapter {
    companion object{
        @BindingAdapter("load_image")
        @JvmStatic
        fun loadImage(imageView : ImageView, imageUrl : String){
            imageView.loadImageView(imageUrl)
        }
    }
}