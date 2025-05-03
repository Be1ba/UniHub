package com.example.unihub.presentation.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.data.model.users.UpdateUserProfileRequest
import com.example.unihub.data.model.users.UserProfileResponse
import com.example.unihub.databinding.FragmentEditProfileBinding
import com.example.unihub.databinding.FragmentEditUserBinding
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.util.Calendar
import kotlin.getValue

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        lateinit var userProfile: UserProfileResponse

        profileViewModel.getUserProfile(sharedProvider.getToken())

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            profileViewModel.profileResponse.observe(viewLifecycleOwner) {
                userProfile = it
                tvMainName.text = it.name + " " + it.surname
                tvMainEmail.text = it.email
                etName.setText(it.name)
                etSurname.setText(it.surname)
                etPhone.setText(it.phone.toString())
                etBirthDate.setText(it.birthDate.toString())
                etGender.setText(it.gender.toString(), false)
            }

            val genders = listOf("Male", "Female")
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
            etGender.setAdapter(adapter)
            etGender.setOnClickListener {
                etGender.showDropDown()
            }

            etBirthDate.setOnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val datePicker = DatePickerDialog(
                    requireContext(),
                    R.style.CustomTimePickerDialogTheme,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                        binding.etBirthDate.text = selectedDate
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePicker.show()
            }

            btnSave.setOnClickListener {
                profileViewModel.updateUserProfile(
                    sharedProvider.getToken(),
                    UpdateUserProfileRequest(
                        birthDate = etBirthDate.text.toString(),
                        clubName = userProfile.clubName.toString(),
                        gender = etGender.text.toString(),
                        name = etName.text.toString(),
                        phone = etPhone.text.toString(),
                        surname = etSurname.text.toString()
                    )
                )
            }
        }

        profileViewModel.updateProfileResponse.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        profileViewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.tvError.text = it.message
            binding.tvError.visibility = View.VISIBLE
        }
    }
}