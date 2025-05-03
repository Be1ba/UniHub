package com.example.unihub.presentation.authentication

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentWelcomeBinding
import com.example.unihub.utils.provideNavigationHost

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            btnCreate.setOnClickListener{
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment())
            }

            tvLogin.setOnClickListener{
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLogInFragment())
            }
        }
    }

}