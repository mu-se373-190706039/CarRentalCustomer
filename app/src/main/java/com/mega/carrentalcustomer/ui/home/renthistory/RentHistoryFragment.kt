package com.mega.carrentalcustomer.ui.home.renthistory


import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mega.carrentalcustomer.data.local.ClientPreferences
import com.mega.carrentalcustomer.databinding.FragmentRentHistoryBinding
import com.mega.carrentalcustomer.model.rentResponse.Rent
import com.mega.carrentalcustomer.ui.home.adapter.RentHistoryAdapter
import com.mega.carrentalcustomer.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentHistoryFragment : BaseFragment<FragmentRentHistoryBinding, RentHistoryViewModel>(
    FragmentRentHistoryBinding::inflate
) {
    override val viewModel by viewModels<RentHistoryViewModel>()
    private var rentHistoryList: MutableList<Rent> = mutableListOf()
    private var temporaryList: MutableList<Rent> = mutableListOf()

    override fun onCreateFinished() {
        viewModel.getReservation(ClientPreferences(requireContext()).getUserEmail().toString())
        setRecyclerViewAdapter()
    }

    override fun initListeners() {
        binding.swipeToRefresh.setOnRefreshListener {
            refresh()
        }
    }

    override fun observeEvents() {
        with(viewModel){
            rentHistoryResponse?.observe(viewLifecycleOwner, Observer {
                it?.let {
                    rentHistoryList.clear()
                    it.rent?.forEach { car ->
                        rentHistoryList.add(car)
                    }
                }
                temporaryList.clear()
                temporaryList.addAll(rentHistoryList)
                (binding.rvRentHistory.adapter as RentHistoryAdapter).updateList(temporaryList)
            })
        }
    }

    private fun setRecyclerViewAdapter() {
        val mLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvRentHistory.layoutManager = mLayoutManager
        val mAdapter = RentHistoryAdapter(requireContext())
        binding.rvRentHistory.adapter = mAdapter
    }

    private fun refresh(){
        val email = ClientPreferences(requireContext()).getUserEmail().toString()
        viewModel.getReservation(email)
        binding.swipeToRefresh.isRefreshing = false
    }

}