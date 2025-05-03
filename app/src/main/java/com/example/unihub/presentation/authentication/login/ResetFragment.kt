package com.example.unihub.presentation.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentResetBinding
import com.example.unihub.utils.provideNavigationHost

class ResetFragment : Fragment() {

    private lateinit var binding: FragmentResetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        var isSent = false

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSend.setOnClickListener {
                if (!isSent) {
                    isSent = true
                    ivEmail.visibility = View.VISIBLE
                    tvEmail.visibility = View.GONE
                    etEmail.visibility = View.GONE
                    val email = etEmail.text.toString()
                    tvSendMsg.text = "We have sent an email \n to ${email} with instructions to reset your password."
                    btnSend.text = "Back to login"
                } else {
                    findNavController().navigate(ResetFragmentDirections.actionResetFragmentToResetSuccessFragment())
                }
            }
        }
    }

}