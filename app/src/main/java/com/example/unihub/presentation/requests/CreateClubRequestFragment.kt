package com.example.unihub.presentation.requests

import android.annotation.SuppressLint
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
import com.example.unihub.databinding.FragmentCreateClubRequestBinding
import com.example.unihub.presentation.admin.AdminViewModel
import com.example.unihub.presentation.calendar.EventsFragmentArgs
import com.example.unihub.presentation.profile.RequestViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.getValue

@SuppressLint("NewApi")
class CreateClubRequestFragment : Fragment() {

    private lateinit var binding: FragmentCreateClubRequestBinding
    private val args: CreateClubRequestFragmentArgs by navArgs()
    private val requestViewModel: RequestViewModel by viewModels()
    private val adminViewModel: AdminViewModel by viewModels()

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
        val sharedProvider = SharedProvider(requireContext())

        requestViewModel.getRequestDetails(sharedProvider.getToken(), args.id)

        requestViewModel.getRequestDetailsResponse.observe(viewLifecycleOwner) { requestDetails ->
            binding.run {
                if (requestDetails != null) {
                    tvSender.text = "Sender: ${requestDetails.email}"
                    tvCreateDate.text = "Date of create: ${formatIsoDate(requestDetails.createdAt)}"
                    tvComment.text = "Comment: ${requestDetails.comment}"
                    etClubName.setText(requestDetails.clubName)
                    etGoal.setText(requestDetails.goal)
                    etFinance.setText(requestDetails.financing)
                    etEquipment.setText(requestDetails.resources)
                    etDescription.setText(requestDetails.description)
                    tvStatus.text = "Status: ${requestDetails.status}"
                    etMethods.setText(requestDetails.attractionMethods)
                    Log.d("Header", requestDetails.headId.toString())
                    // Head ID is not used in the UI, but you can use it if needed
                    etHead.setText(requestDetails.email)
                    etEmail.setText(requestDetails.email)
                    etPhone.setText(requestDetails.phone)
                    etContacts.setText(requestDetails.communication)
                    etComment.setText(requestDetails.comment)
                }
            }
        }


        binding.run {
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
                btnSend.visibility = View.VISIBLE
            } else {
                tvComment.visibility = View.VISIBLE
                etStatus.visibility = View.GONE
                btnSend.visibility = View.GONE
            }

            btnBack.setOnClickListener{
                findNavController().popBackStack()
            }

            btnSend.setOnClickListener {
                val status = etStatus.text.toString()
                if (status == "Approved") {
                    adminViewModel.approveClubRequest(sharedProvider.getToken(), args.id)
                } else {
                    adminViewModel.rejectClubRequest(sharedProvider.getToken(), args.id)
                }
            }

            adminViewModel.approveClubRequestResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    findNavController().navigate(CreateClubRequestFragmentDirections.actionCreateClubRequestFragmentToRequestConfirmSuccessFragment())
                }
            }

            adminViewModel.rejectClubRequestResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    findNavController().navigate(CreateClubRequestFragmentDirections.actionCreateClubRequestFragmentToRequestConfirmSuccessFragment())
                }
            }

            adminViewModel.errorMessage.observe(viewLifecycleOwner) {
                binding.tvError.text = it.message
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }

    fun formatIsoDate(isoString: String): String {
        val zonedDateTime = ZonedDateTime.parse(isoString)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
        return zonedDateTime.format(formatter)
    }
}