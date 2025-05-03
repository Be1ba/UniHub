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
import com.example.unihub.databinding.FragmentVerifyBinding
import com.example.unihub.presentation.authentication.AuthViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class VerifyFragment : Fragment() {

    private lateinit var binding: FragmentVerifyBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyBinding.inflate(inflater, container, false)
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

            btnVerify.setOnClickListener {
                val email = sharedProvider.getEmail()
                val code = etCode.text.toString()
                Log.d("Verify", "Email: $email, Code: $code")
            }

            etCode.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            authViewModel.errorMessage.observe(viewLifecycleOwner) {
                tvError.text = it.message
                tvError.visibility = View.VISIBLE
            }

            tvDifferentEmail.setOnClickListener {
                findNavController().navigate(VerifyFragmentDirections.actionVerifyFragmentToSignInFragment())
            }
        }
    }
}