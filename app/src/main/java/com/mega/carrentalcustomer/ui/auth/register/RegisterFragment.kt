package com.mega.carrentalcustomer.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mega.carrentalcustomer.R
import com.mega.carrentalcustomer.data.local.ClientPreferences
import com.mega.carrentalcustomer.databinding.FragmentRegisterBinding
import com.mega.carrentalcustomer.util.base.BaseFragment
import com.mega.carrentalcustomer.util.extension.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding,RegisterViewModel>(
    FragmentRegisterBinding::inflate
) {
    private var name : String = ""
    private var email : String = ""
    private var password : String = ""
    override val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateFinished() {

    }

    override fun initListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }

        })

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }

        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }
        })

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    override fun observeEvents() {
        with(viewModel){
            registerResponse?.observe(viewLifecycleOwner, Observer {
                if(it.status == "true"){
                    snack(requireView(),"Registered Successfully")
                    ClientPreferences(requireContext()).setUserEmail(email)
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                } else if(it.status == "false"){
                    snack(requireView(),it.message.toString())
                }
            })

            isLoading.observe(viewLifecycleOwner, Observer {
                handleViewAction(it)
            })

            onError.observe(viewLifecycleOwner, Observer {
                snack(requireView(),it.toString())
            })
        }
    }

    private fun checkFields(){
        if (!binding.etName.text.isNullOrEmpty() && !binding.etEmail.text.isNullOrEmpty() && !binding.etPassword.text.isNullOrEmpty()) {
            binding.btnRegister.isEnabled = true
            binding.btnRegister.alpha = 1F
        } else {
            binding.btnRegister.isEnabled = false
            binding.btnRegister.alpha = 0.6F
        }
    }

    private fun handleViewAction(isLoading : Boolean = false){
        binding.progressBar.isVisible = isLoading
    }

    private fun register(){
        name = binding.etName.text.toString()
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()

        viewModel.register(name,email,password)
    }
}