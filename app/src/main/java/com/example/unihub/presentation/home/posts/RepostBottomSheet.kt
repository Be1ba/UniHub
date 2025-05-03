package com.example.unihub.presentation.home.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentRepostBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RepostBottomSheet(val postID: Int) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRepostBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepostBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            llRepost.setOnClickListener {
                findNavController().navigate(RepostBottomSheetDirections.actionRepostBottomSheetToUserProfileFragment())
                dismiss()
            }

            llQuote.setOnClickListener {
                dismiss()
            }
        }
    }
}