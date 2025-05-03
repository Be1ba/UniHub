package com.example.unihub.presentation.home.club

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unihub.R
import com.example.unihub.databinding.FragmentRegisterBookingBinding
import com.example.unihub.presentation.club.CreateNewPostFragment
import com.example.unihub.utils.provideNavigationHost

class RegisterBookingFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBookingBinding
    private val args: RegisterBookingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            btnRegister.setOnClickListener {
                findNavController().navigate(RegisterBookingFragmentDirections.actionRegisterBookingFragmentToClubPageFragment(id = args.id, type = "book"))
            }

            ivDropFiles.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                startActivityForResult(intent,
                    CreateNewPostFragment.Companion.REQUEST_CODE_PICK_MEDIA
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_MEDIA && resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImageUri = data?.data
            // Здесь вы можете обработать выбранное изображение, например, отобразить его
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_MEDIA = 1001
    }
}