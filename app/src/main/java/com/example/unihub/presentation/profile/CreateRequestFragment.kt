package com.example.unihub.presentation.profile

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.R
import com.example.unihub.data.model.club_request.CreateClubRequest
import com.example.unihub.databinding.FragmentCreateRequestBinding
import com.example.unihub.presentation.requests.RequestItemAdapter
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class CreateRequestFragment : Fragment() {

    private lateinit var binding: FragmentCreateRequestBinding
    private val requestViewModel: RequestViewModel by viewModels()
    private lateinit var sharedProvider: SharedProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        sharedProvider = SharedProvider(requireContext())

        requestViewModel.getMyRequests(sharedProvider.getToken())

        val requestItemAdapter = RequestItemAdapter()

        requestItemAdapter.setOnItemClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(
                        CreateRequestFragmentDirections.actionCreateRequestFragmentToCreateClubRequestFragment(id ?: 1, false)
                    )
                }
            }
        )

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            requestViewModel.myRequestsResponse.observe(viewLifecycleOwner) {
                requestItemAdapter.submitList(it)
            }

            requestViewModel.createClubResponse.observe(viewLifecycleOwner) {
                requestViewModel.getMyRequests(sharedProvider.getToken())
                llRequests.visibility = View.GONE
                rvMyRequests.visibility = View.VISIBLE
                idRequests.visibility = View.INVISIBLE
                idMyRequests.visibility = View.VISIBLE
            }

            requestViewModel.errorMessage.observe(viewLifecycleOwner) {
                tvError.text = it.message
                tvError.visibility = View.VISIBLE
            }

            tvRequests.setOnClickListener {
                llRequests.visibility = View.VISIBLE
                rvMyRequests.visibility = View.GONE
                idRequests.visibility = View.VISIBLE
                idMyRequests.visibility = View.INVISIBLE
            }

            tvMyRequests.setOnClickListener {
                requestViewModel.getMyRequests(sharedProvider.getToken())
                llRequests.visibility = View.GONE
                rvMyRequests.visibility = View.VISIBLE
                idRequests.visibility = View.INVISIBLE
                idMyRequests.visibility = View.VISIBLE
            }

            rvMyRequests.adapter = requestItemAdapter
            rvMyRequests.layoutManager = LinearLayoutManager(requireContext())

            btnSend.setOnClickListener {
                showCustomDialogBox()
            }
        }
    }

    private fun showCustomDialogBox() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_log_out)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        val tvTitle: TextView = dialog.findViewById(R.id.tvTitle)
        val btnDismiss: TextView = dialog.findViewById(R.id.btnNo)
        val btnLogout: TextView = dialog.findViewById(R.id.btnYes)

        tvTitle.text = "Do you want to send?"

        btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        btnLogout.setOnClickListener {
            requestViewModel.createClubRequest(
                sharedProvider.getToken(),
                CreateClubRequest(
                    attractionMethods = binding.etMethods.text.toString(),
                    clubName = binding.etClubName.text.toString(),
                    comment = binding.etComment.text.toString(),
                    communication = binding.etContacts.text.toString(),
                    description = binding.etDescription.text.toString(),
                    financing = binding.etFinance.text.toString(),
                    goal = binding.etGoal.text.toString(),
                    phone = binding.etPhone.text.toString(),
                    resources = binding.etEquipment.text.toString(),
                    title = binding.etTitle.text.toString(),
                )
            )
            dialog.dismiss()
        }

        dialog.show()
    }
}