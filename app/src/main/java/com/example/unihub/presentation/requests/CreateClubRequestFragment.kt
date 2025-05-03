package com.example.unihub.presentation.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreateClubRequestBinding
import com.example.unihub.presentation.calendar.EventsFragmentArgs
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class CreateClubRequestFragment : Fragment() {

    private lateinit var binding: FragmentCreateClubRequestBinding
    private val args: CreateClubRequestFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateClubRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            if (args.fromAdmin) {
                tvComment.visibility = View.GONE
                etStatus.visibility = View.VISIBLE
            } else {
                tvComment.visibility = View.VISIBLE
                etStatus.visibility = View.GONE
            }

            btnBack.setOnClickListener{
                findNavController().popBackStack()
            }

            btnSend.setOnClickListener {
                findNavController().navigate(CreateClubRequestFragmentDirections.actionCreateClubRequestFragmentToRequestConfirmSuccessFragment())
            }
        }
    }
}