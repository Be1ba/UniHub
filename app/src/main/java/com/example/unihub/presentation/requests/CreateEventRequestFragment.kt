package com.example.unihub.presentation.requests

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreateEventRequestBinding
import com.example.unihub.presentation.profile.RequestViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class CreateEventRequestFragment : Fragment() {

    private lateinit var binding: FragmentCreateEventRequestBinding
    private val viewModel: RequestViewModel by viewModels()
    private val args: CreateEventRequestFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateEventRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        viewModel.getRequestDetails(sharedProvider.getToken(), args.id)

        binding.run {
            viewModel.getRequestDetailsResponse.observe(viewLifecycleOwner) { requestDetails->
                if (requestDetails != null) {
                    tvSender.text = "Sender: ${requestDetails.email}"
                    tvCreateDate.text = "Date of create: ${formatIsoDate(requestDetails.createdAt)}"
                    tvComment.text = "Comment: ${requestDetails.comment}"
                    etEventName.setText(requestDetails.title)
                    etGoal.setText(requestDetails.goal)
                    etDescription.setText(requestDetails.description)
                    etOrganizers.setText(requestDetails.communication)
                    etLocation.setText(requestDetails.communication)
                    etHead.setText(requestDetails.email)
                    etPhone.setText(requestDetails.phone)
                    etComment.setText(requestDetails.comment)
                }
            }

            val items = listOf("Approved", "Cancelled")
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
            etStatus.setAdapter(adapter)
//            acTemplate.setText("Choose template", false)
            etStatus.setOnClickListener {
                etStatus.showDropDown()
            }

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
                val status = etStatus.text.toString()
                if (status == "Approved") {
                    viewModel.approveRequest(sharedProvider.getToken(), args.id)
                } else {
                    viewModel.rejectRequest(sharedProvider.getToken(), args.id)
                }
                findNavController().navigate(CreateEventRequestFragmentDirections.actionCreateEventRequestFragmentToRequestConfirmSuccessFragment())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatIsoDate(isoString: String): String {
        val zonedDateTime = ZonedDateTime.parse(isoString)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
        return zonedDateTime.format(formatter)
    }
}