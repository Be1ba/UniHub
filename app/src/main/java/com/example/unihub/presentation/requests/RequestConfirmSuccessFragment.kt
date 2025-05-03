package com.example.unihub.presentation.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentRequestConfirmSuccessBinding
import com.example.unihub.utils.provideNavigationHost

class RequestConfirmSuccessFragment : Fragment() {

    private lateinit var binding: FragmentRequestConfirmSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestConfirmSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(RequestConfirmSuccessFragmentDirections.actionRequestConfirmSuccessFragmentToRequestsListFragment())
        }
    }
}