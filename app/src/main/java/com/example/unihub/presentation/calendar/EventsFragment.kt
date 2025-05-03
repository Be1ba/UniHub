package com.example.unihub.presentation.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unihub.databinding.FragmentEventsBinding
import com.example.unihub.utils.provideNavigationHost

class EventsFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding
    private val args: EventsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            tvEventName.text = args.id.toString()
        }
    }
}