package com.mega.carrentalcustomer.ui.home



import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.mega.carrentalcustomer.MainActivity
import com.mega.carrentalcustomer.R
import com.mega.carrentalcustomer.data.local.ClientPreferences
import com.mega.carrentalcustomer.databinding.FragmentHomeBinding
import com.mega.carrentalcustomer.model.carsResponse.Car
import com.mega.carrentalcustomer.ui.home.adapter.CarsAdapter
import com.mega.carrentalcustomer.util.base.BaseFragment
import com.mega.carrentalcustomer.util.extension.OnItemClickListener
import com.mega.carrentalcustomer.util.extension.snack

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel by viewModels<HomeViewModel>()
    private var carList : MutableList<Car> = mutableListOf()
    private var temporaryList: MutableList<Car> = mutableListOf()

    //Navigation Drawer
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var headerView: View
    private lateinit var headerEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchAllCars()
    }
    override fun onCreateFinished() {
        setRecyclerViewAdapter()
        setNavigationDrawer()
    }

    override fun initListeners() {
        binding.fabOpenNavDrawer.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
        binding.swipeToRefresh.setOnRefreshListener {
            refresh()
        }
    }

    override fun observeEvents() {
        with(viewModel){
            carsResponse?.observe(viewLifecycleOwner, Observer {
                it?.let {
                    carList.clear()
                    it.cars?.forEach { car ->
                        carList.add(car)
                    }
                }
                temporaryList.clear()
                temporaryList.addAll(carList)
                (binding.rvCars.adapter as CarsAdapter).updateList(temporaryList)
            })

            isLoading.observe(viewLifecycleOwner, Observer {
                handleViewActions(it)
            })

            onError.observe(viewLifecycleOwner, Observer {
                snack(requireView(),it.toString())
            })
        }
    }

    private fun setRecyclerViewAdapter(){
        val mLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvCars.layoutManager = mLayoutManager
        val mAdapter = CarsAdapter(requireContext(),object : OnItemClickListener{
            override fun onClick(car: Car) {
                val action = HomeFragmentDirections.actionHomeFragmentToRentDetailFragment(car)
                findNavController().navigate(action)
            }
        })
        binding.rvCars.adapter = mAdapter
    }

    private fun setNavigationDrawer(){
        drawerLayout = binding.drawerLayout
        navView = binding.navView
        headerView = navView.getHeaderView(0)
        headerEmail = headerView.findViewById(R.id.tvUserEmail)
        headerEmail.text = ClientPreferences(requireContext()).getUserEmail().toString()

        toggle = ActionBarDrawerToggle(
            activity as MainActivity,
            drawerLayout,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.inflateMenu(R.menu.nav_menu)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_feedback -> {}
                R.id.nav_logout -> logout()
                R.id.nav_history -> findNavController().navigate(R.id.action_homeFragment_to_rentHistoryFragment)
            }
            true
        }
    }

    private fun handleViewActions(isLoading: Boolean = false) {
        binding.rvCars.isVisible = !isLoading
        binding.progressBar.isVisible = isLoading
    }

    private fun logout(){
        ClientPreferences(requireContext()).clearSharedPref()
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }
    private fun refresh(){
        viewModel.fetchAllCars()
        binding.swipeToRefresh.isRefreshing = false
    }

}