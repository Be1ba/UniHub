package com.example.unihub.presentation.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.databinding.FragmentActivitiesBinding
import com.example.unihub.presentation.requests.RequestItemAdapter
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost

class ActivitiesFragment : Fragment() {

    private lateinit var binding: FragmentActivitiesBinding
    private val notificationsViewModel: NotificationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)
        val sharedProvider = SharedProvider(requireContext())

        notificationsViewModel.getTickets(sharedProvider.getToken())

        val notificationsAdapter = NotificationsItemAdapter()

        notificationsViewModel.getTicketsResponse.observe(viewLifecycleOwner) {
            notificationsAdapter.submitList(it)
        }

        notificationsAdapter.setOnItemClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(ActivitiesFragmentDirections.actionActivitiesFragmentToBookingDetailsFragment(id!!))
                }
            }
        )

        binding.run {
            rvNotifications.adapter = notificationsAdapter
            rvNotifications.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}