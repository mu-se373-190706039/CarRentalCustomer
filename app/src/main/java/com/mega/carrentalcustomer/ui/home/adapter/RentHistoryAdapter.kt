package com.mega.carrentalcustomer.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mega.carrentalcustomer.R
import com.mega.carrentalcustomer.databinding.RentItemRowBinding
import com.mega.carrentalcustomer.model.rentResponse.Rent


class RentHistoryAdapter(
    private val context: Context
): RecyclerView.Adapter<RentHistoryAdapter.ViewHolder>() {
    private var rentHistoryList = mutableListOf<Rent>()

    class ViewHolder(private val itemBinding : RentItemRowBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(rent : Rent, context: Context){
            itemBinding.rentHistoryResponse = rent
            itemBinding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RentItemRowBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rentHistoryList[position],context)
    }

    override fun getItemCount(): Int = rentHistoryList.size

    fun updateList(_rentHistoryList : MutableList<Rent>){
        rentHistoryList = _rentHistoryList
        notifyDataSetChanged()
    }
}