package com.mega.carrentalcustomer.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mega.carrentalcustomer.databinding.CarItemBinding
import com.mega.carrentalcustomer.model.carsResponse.Car
import com.mega.carrentalcustomer.util.extension.OnItemClickListener

class CarsAdapter(
    private val context: Context,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CarsAdapter.ViewHolder>() {
    private var carsList = mutableListOf<Car>()
    class ViewHolder(private val itemBinding: CarItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(listener : OnItemClickListener, car : Car, context: Context){
            itemBinding.onItemClickListener = listener
            itemBinding.carsResponse = car
            itemBinding.executePendingBindings()

            if(car.state.equals("reserved")){
                itemBinding.btnRent.alpha = 0.2F
                itemBinding.btnRent.isClickable = false
            }
            else if(car.state.equals("free")){
                itemBinding.btnRent.alpha = 1F
                itemBinding.btnRent.isClickable = true
            }
        }
        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CarItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onItemClickListener,carsList[position],context)
    }

    override fun getItemCount(): Int = carsList.size

    fun updateList(_carsList : MutableList<Car>){
        carsList = _carsList
        notifyDataSetChanged()
    }
}