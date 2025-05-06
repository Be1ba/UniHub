package com.example.unihub.presentation.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.databinding.FragmentRequestsListBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost

class RequestsListFragment : Fragment() {

    private lateinit var binding: FragmentRequestsListBinding
    private val viewModel: AdminViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        viewModel.getRequestsList(sharedProvider.getToken())
        viewModel.getEventsList(sharedProvider.getToken())

        val adapterClub = ClubRequestItemAdapter()
        val adapterEvent = EventRequestItemAdapter()

        viewModel.getRequestsListResponse.observe(viewLifecycleOwner) { requestsList ->
            adapterClub.submitList(requestsList)
        }

        viewModel.getEventsListResponse.observe(viewLifecycleOwner) { eventsList ->
            adapterEvent.submitList(eventsList)
        }

        adapterClub.setOnItemClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(RequestsListFragmentDirections.actionRequestsListFragmentToCreateClubRequestFragment(id!!, true))
                }
            }
        )

        adapterEvent.setOnItemClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(RequestsListFragmentDirections.actionRequestsListFragmentToCreateEventRequestFragment(id!!, true))
                }
            }
        )

        binding.run {
            btnBack.setOnClickListener {
                findNavController().navigate(RequestsListFragmentDirections.actionRequestsListFragmentToAdminPageFragment())
            }

            rvEventRequestsList.adapter = adapterEvent
            rvClubRequestsList.adapter = adapterClub

            tvClubRequests.setOnClickListener {
                idClubRequests.visibility = View.VISIBLE
                idEventRequests.visibility = View.INVISIBLE

                rvEventRequestsList.visibility = View.GONE
                rvClubRequestsList.visibility = View.VISIBLE
            }

            tvEventRequests.setOnClickListener {
                idClubRequests.visibility = View.INVISIBLE
                idEventRequests.visibility = View.VISIBLE

                rvEventRequestsList.visibility = View.VISIBLE
                rvClubRequestsList.visibility = View.GONE
            }
        }
    }

}