package com.example.unihub.presentation.authentication.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.databinding.FragmentSignInBinding
import com.example.unihub.presentation.authentication.AuthViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            etName.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etSurname.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etEmail.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etPassword1.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etPassword2.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            btnCreate.setOnClickListener {
                if (
                    etPassword1.text.toString().isEmpty() ||
                    etPassword2.text.toString().isEmpty() ||
                    etEmail.text.toString().isEmpty() ||
                    etName.text.toString().isEmpty() ||
                    etSurname.text.toString().isEmpty()
                ) {
                    tvError.text = "Please fill all fields"
                    tvError.visibility = View.VISIBLE
                    return@setOnClickListener
                } else if (etPassword1.text.toString() == etPassword2.text.toString()) {
                    tvError.visibility = View.INVISIBLE
                    val email = etEmail.text.toString().trim()
                    val password = etPassword1.text.toString().trim()
                    val name = etName.text.toString().trim()
                    val surname = etSurname.text.toString().trim()
                    authViewModel.signup(email = email, password = password, name = name, surname = surname)
                } else {
                    tvError.text = "Passwords are not same"
                    tvError.visibility = View.VISIBLE
                }
            }

            authViewModel.signupResponse.observe(viewLifecycleOwner) {
                Log.d("Login Response", it.toString())
                sharedProvider.saveToken(it.token.toString())
                sharedProvider.saveEmail(it.user.email.toString())
                sharedProvider.saveName(it.user.name.toString())
                sharedProvider.saveSurname(it.user.surname.toString())
                sharedProvider.saveRole(it.user.role.toString())
                sharedProvider.saveID(it.user.id)
                sharedProvider.saveIsAuthorized(true)
                provideNavigationHost()?.setupBottomNavForRole(it.user.role.toString().lowercase().contains("admin"))
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
            }

            authViewModel.errorMessage.observe(viewLifecycleOwner) {
                tvError.text = it.message
                tvError.visibility = View.VISIBLE
            }
        }
    }

}