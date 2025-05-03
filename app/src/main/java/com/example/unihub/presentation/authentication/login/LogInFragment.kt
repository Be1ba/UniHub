package com.example.unihub.presentation.authentication.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentLogInBinding
import com.example.unihub.presentation.authentication.AuthViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        binding.run {
            btnBack.setOnClickListener{
                findNavController().popBackStack()
            }

            btnLogin.setOnClickListener{
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                Log.d("Login", "Email: $email, Password: $password")
                authViewModel.login(email = email, password = password)
            }

            etEmail.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etPassword.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etPassword.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val drawableEnd = etPassword.compoundDrawablesRelative[2]
                    if (drawableEnd != null && event.rawX >= (etPassword.right - etPassword.paddingEnd - drawableEnd.bounds.width())) {
                        if (etPassword.inputType == android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD or android.text.InputType.TYPE_CLASS_TEXT) {
                            etPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_hide, 0)
                        } else {
                            etPassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD or android.text.InputType.TYPE_CLASS_TEXT
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_hide, 0)
                        }
                        etPassword.setSelection(etPassword.text?.length ?: 0)
                        return@setOnTouchListener true
                    }
                }
                false
            }

            authViewModel.loginResponse.observe(viewLifecycleOwner) {
                Log.d("Login Response", it.message.toString())
                sharedProvider.saveToken(it.token.toString())
                sharedProvider.saveEmail(it.user.email.toString())
                sharedProvider.saveName(it.user.name.toString())
                sharedProvider.saveSurname(it.user.surname.toString())
                sharedProvider.saveRole(it.user.role.toString())
                sharedProvider.saveID(it.user.id)
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
            }

            authViewModel.errorMessage.observe(viewLifecycleOwner) {
                tvError.text = it.message
                tvError.visibility = View.VISIBLE
            }

            tvForgotPassword.setOnClickListener{
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToResetFragment())
            }
        }

    }

}