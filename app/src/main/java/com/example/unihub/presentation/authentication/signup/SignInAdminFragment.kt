package com.example.unihub.presentation.authentication.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentSignInAdminBinding
import com.example.unihub.utils.provideNavigationHost

class SignInAdminFragment : Fragment() {

    private lateinit var binding: FragmentSignInAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnCreate.setOnClickListener {
                findNavController().navigate(SignInAdminFragmentDirections.actionSignInAdminFragmentToVerifyFragment())
            }
        }
    }

}