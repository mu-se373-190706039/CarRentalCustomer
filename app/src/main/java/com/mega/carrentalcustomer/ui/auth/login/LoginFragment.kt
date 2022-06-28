package com.mega.carrentalcustomer.ui.auth.login


import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mega.carrentalcustomer.R
import com.mega.carrentalcustomer.data.local.ClientPreferences
import com.mega.carrentalcustomer.databinding.FragmentLoginBinding
import com.mega.carrentalcustomer.util.base.BaseFragment
import com.mega.carrentalcustomer.util.extension.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding,LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    private var email: String = ""
    private var password: String = ""
    override val viewModel by viewModels<LoginViewModel>()

    override fun onCreateFinished() {
        isUserLoggedIn()
    }

    override fun initListeners() {
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

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvRegister.setOnClickListener {
            navigateToRegisterFragment()
        }
    }

    override fun observeEvents() {
        with(viewModel){
            loginResponse?.observe(viewLifecycleOwner, Observer {
                if(it.status == "true"){
                    snack(requireView(),"Login Successful")
                    ClientPreferences(requireContext()).setUserEmail(email)
                    ClientPreferences(requireContext()).setRememberMe(true)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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

    private fun isUserLoggedIn() {
        if (ClientPreferences(requireContext()).isRememberMe()) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun handleViewAction(isLoading: Boolean = false) {
        binding.progressBar.isVisible = isLoading
    }

    private fun checkFields() {
        if (!binding.etEmail.text.isNullOrEmpty() && !binding.etPassword.text.isNullOrEmpty()) {
            binding.btnLogin.isEnabled = true
            binding.btnLogin.alpha = 1F
        } else {
            binding.btnLogin.isEnabled = false
            binding.btnLogin.alpha = 0.2F
        }
    }

    private fun login() {
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()

        viewModel.login(email, password)
    }

    private fun navigateToRegisterFragment(){
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

}