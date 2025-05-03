package com.example.unihub.presentation.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentAddUserSuccessBinding
import com.example.unihub.utils.provideNavigationHost

class AddUserSuccessFragment : Fragment() {

    private lateinit var binding: FragmentAddUserSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(AddUserSuccessFragmentDirections.actionAddUserSuccessFragmentToAdminPageFragment())
        }
    }

}