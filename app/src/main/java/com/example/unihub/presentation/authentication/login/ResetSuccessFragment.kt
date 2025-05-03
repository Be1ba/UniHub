package com.example.unihub.presentation.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentResetSuccessBinding
import com.example.unihub.utils.provideNavigationHost

class ResetSuccessFragment : Fragment() {

    private lateinit var binding: FragmentResetSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnLogin.setOnClickListener {
                findNavController().navigate(ResetSuccessFragmentDirections.actionResetSuccessFragmentToLogInFragment())
            }
        }
    }
}