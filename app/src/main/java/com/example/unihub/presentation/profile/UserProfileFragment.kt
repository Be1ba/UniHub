package com.example.unihub.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unihub.databinding.FragmentUserProfileBinding
import com.example.unihub.utils.provideNavigationHost

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)

        binding.run {
            btnSettings.setOnClickListener {
                findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToSettingsFragment())
            }

            btnEditUser.setOnClickListener {
                findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToProfileFragment())
            }

            btnWrite.setOnClickListener {
                findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToCreateRequestFragment())
            }

            tvReposts.setOnClickListener {
                rvReposts.visibility = View.VISIBLE
                rvReplies.visibility = View.GONE
                idReposts.visibility = View.VISIBLE
                idReplies.visibility = View.INVISIBLE
            }

            tvReplies.setOnClickListener {
                rvReposts.visibility = View.GONE
                rvReplies.visibility = View.VISIBLE
                idReposts.visibility = View.INVISIBLE
                idReplies.visibility = View.VISIBLE
            }
        }
    }
}