package com.example.unihub.presentation.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentChangePasswordBinding
import com.example.unihub.presentation.authentication.AuthViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class ChangePasswordFragment : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            etCurrentPassword.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val drawableEnd = etCurrentPassword.compoundDrawablesRelative[2]
                    if (drawableEnd != null && event.rawX >= (etCurrentPassword.right - etCurrentPassword.paddingEnd - drawableEnd.bounds.width())) {
                        if (etCurrentPassword.inputType == android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD or android.text.InputType.TYPE_CLASS_TEXT) {
                            etCurrentPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                            etCurrentPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_hide, 0)
                        } else {
                            etCurrentPassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD or android.text.InputType.TYPE_CLASS_TEXT
                            etCurrentPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_hide, 0)
                        }
                        etCurrentPassword.setSelection(etCurrentPassword.text?.length ?: 0)
                        return@setOnTouchListener true
                    }
                }
                false
            }

            etNewPassword.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val drawableEnd = etNewPassword.compoundDrawablesRelative[2]
                    if (drawableEnd != null && event.rawX >= (etNewPassword.right - etNewPassword.paddingEnd - drawableEnd.bounds.width())) {
                        if (etNewPassword.inputType == android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD or android.text.InputType.TYPE_CLASS_TEXT) {
                            etNewPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                            etNewPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_hide, 0)
                        } else {
                            etNewPassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD or android.text.InputType.TYPE_CLASS_TEXT
                            etNewPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_hide, 0)
                        }
                        etNewPassword.setSelection(etNewPassword.text?.length ?: 0)
                        return@setOnTouchListener true
                    }
                }
                false
            }

            btnChange.setOnClickListener {
                var currentPassword = etCurrentPassword.text.toString()
                var newPassword = etNewPassword.text.toString()

                profileViewModel.changePassword(sharedProvider.getToken(), currentPassword, newPassword)
            }
        }

        profileViewModel.changePasswordResponse.observe(viewLifecycleOwner) {
            findNavController().navigate(ChangePasswordFragmentDirections.actionChangePasswordFragmentToSuccessChangePasswordFragment())
        }

        profileViewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.tvError.visibility = View.VISIBLE
            binding.tvError.text = it.message
        }
    }
}