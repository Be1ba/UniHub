package com.example.unihub.presentation.admin

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentAdminPageBinding
import com.example.unihub.presentation.requests.RequestItemAdapter
import com.example.unihub.utils.RcViewItemClickIdStringCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost

class AdminPageFragment : Fragment() {

    private lateinit var binding: FragmentAdminPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        val adapterUsers = UsersListItemAdapter()
        val adapterClubs = UsersListItemAdapter()

        adapterUsers.submitList(listOf("User1", "User2", "User3", "User4", "User5", "User6", "User7", "User8", "User9", "User10"))
        adapterClubs.submitList(listOf("Club1", "Club2", "Club3", "Club4", "Club5", "Club6", "Club7", "Club8", "Club9", "Club10"))

        adapterUsers.setOnDeleteClickListener(
            object : RcViewItemClickIdStringCallback {
                override fun onClick(id: String) {
                    showCustomDialogBox()
                }
            }
        )

        adapterUsers.setOnUserNameClickListener(
            object : RcViewItemClickIdStringCallback {
                override fun onClick(id: String) {
                    findNavController().navigate(AdminPageFragmentDirections.actionAdminPageFragmentToEditUserFragment())
                }
            }
        )

        adapterClubs.setOnDeleteClickListener(
            object : RcViewItemClickIdStringCallback {
                override fun onClick(id: String) {
                    showCustomDialogBox()
                }
            }
        )

        adapterClubs.setOnUserNameClickListener(
            object : RcViewItemClickIdStringCallback {
                override fun onClick(id: String) {
                    findNavController().navigate(AdminPageFragmentDirections.actionAdminPageFragmentToEditUserFragment())
                }
            }
        )

        binding.run {
            btnAddUser.setOnClickListener {
                findNavController().navigate(AdminPageFragmentDirections.actionAdminPageFragmentToAddUserFragment())
            }

            tvUsersList.setOnClickListener {
                tvUsersList.setTextColor(resources.getColor(R.color.grey_600))
                tvClubsList.setTextColor(resources.getColor(R.color.black_900))

                rvUsersList.visibility = View.VISIBLE
                rvClubsList.visibility = View.GONE
            }

            tvClubsList.setOnClickListener {
                tvUsersList.setTextColor(resources.getColor(R.color.black_900))
                tvClubsList.setTextColor(resources.getColor(R.color.grey_600))

                rvUsersList.visibility = View.GONE
                rvClubsList.visibility = View.VISIBLE
            }

            rvUsersList.adapter = adapterUsers
            rvClubsList.adapter = adapterClubs
        }
    }

    private fun showCustomDialogBox() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_log_out)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        val btnDismiss: TextView = dialog.findViewById(R.id.btnNo)
        val btnLogout: TextView = dialog.findViewById(R.id.btnYes)

        val tvTitle: TextView = dialog.findViewById(R.id.tvTitle)

        tvTitle.text = "Are you sure you want to delete this user?"

        btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        btnLogout.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}