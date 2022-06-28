package com.mega.carrentalcustomer.ui.home.detail

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.mega.carrentalcustomer.MainActivity
import com.mega.carrentalcustomer.data.local.ClientPreferences
import com.mega.carrentalcustomer.databinding.FragmentRentDetailBinding
import com.mega.carrentalcustomer.util.base.BaseFragment
import com.mega.carrentalcustomer.util.extension.loadImageView
import com.mega.carrentalcustomer.util.extension.snack
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RentDetailFragment : BaseFragment<FragmentRentDetailBinding, RentDetailViewModel>(
    FragmentRentDetailBinding::inflate
) {
    override val viewModel by viewModels<RentDetailViewModel>()
    private val args by navArgs<RentDetailFragmentArgs>()
    private var startDate : Long = 0
    private var endDate : Long = 0

    override fun onCreateFinished() {

    }

    override fun initListeners() {
        binding.ivCarPhoto.loadImageView(args.carDetail.carPhoto.toString())
        binding.tvCarName.text = args.carDetail.carName.toString()
        binding.tvOwnerName.text = args.carDetail.ownerName.toString()
        binding.tvFuel.text = "Fuel: " + args.carDetail.fuel.toString()
        binding.tvDeposit.text = "Deposit: " + args.carDetail.deposit.toString()
        binding.tvDailyFee.text = "Daily Fee: " + args.carDetail.dailyFee.toString()

        binding.ibDate.setOnClickListener {
            showDataRangePicker()
        }

        binding.btnRent.setOnClickListener {
            rentCar()
        }
    }

    override fun observeEvents() {
        with(viewModel) {
            rentResponse?.observe(viewLifecycleOwner, Observer {
                it?.let {
                    snack(requireView(), it.message.toString())
                }
            })
            onError.observe(viewLifecycleOwner, Observer {
                snack(requireView(), it.toString())
            })
        }
    }

    private fun rentCar() {
        val adId = args.carDetail.adId.toString()
        val ownerId = args.carDetail.ownerId.toString()
        val ownerName = args.carDetail.ownerName.toString()
        val carName = args.carDetail.carName.toString()
        val email = ClientPreferences(requireContext()).getUserEmail().toString()
        val dailyFee = args.carDetail.dailyFee.toString()
        val state = args.carDetail.state.toString()

        viewModel.rentCar(adId, ownerId, ownerName, carName, email, dailyFee, convertLongToTime(startDate),convertLongToTime(endDate))
    }

    @SuppressLint("SetTextI18n")
    private fun showDataRangePicker() {
        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Date")
                .build()

        dateRangePicker.show(
            (activity as MainActivity).supportFragmentManager,
            "date_range_picker"
        )
        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->

            startDate = dateSelected.first
            endDate = dateSelected.second

            if (startDate != null && endDate != null) {
                binding.tvStartDate.text =
                    "Start Date: ${convertLongToTime(startDate)}"
                binding.tvEndDate.text =
                    "End Date: ${convertLongToTime(endDate)}"
            }
        }
    }


    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault()
        )
        return format.format(date)
    }
}