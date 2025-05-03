package com.example.unihub.presentation.club

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreatePosterBinding
import java.util.Calendar

class CreatePosterFragment : Fragment() {

    private lateinit var binding: FragmentCreatePosterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            btnBack.setOnClickListener {
                findNavController().navigate(CreatePosterFragmentDirections.actionCreatePosterFragmentToCreateNewPostFragment())
            }

            etTime.setOnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val timePicker = TimePickerDialog(
                    requireContext(),
                    R.style.CustomTimePickerDialogTheme,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                        binding.etTime.text = selectedTime
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePicker.show()
            }

            etDate.setOnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val datePicker = DatePickerDialog(
                    requireContext(),
                    R.style.CustomTimePickerDialogTheme,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                        binding.etDate.text = selectedDate
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePicker.show()
            }

            etMedia.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                startActivityForResult(intent, REQUEST_CODE_PICK_MEDIA)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_MEDIA && resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImageUri = data?.data
            // Здесь вы можете обработать выбранное изображение, например, отобразить его
            binding.etMedia.text = selectedImageUri?.toString()
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_MEDIA = 1001
    }
}