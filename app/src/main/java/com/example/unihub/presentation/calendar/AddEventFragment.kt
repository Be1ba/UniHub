package com.example.unihub.presentation.calendar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentAddEventBinding
import com.example.unihub.utils.provideNavigationHost
import java.util.Calendar

class AddEventFragment : Fragment() {

    private lateinit var binding: FragmentAddEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForColorStateLists", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            btnCreate.setOnClickListener {
                findNavController().popBackStack()
            }

            etTimeStart.setOnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val timePicker = TimePickerDialog(
                    requireContext(),
                    R.style.CustomTimePickerDialogTheme,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                        binding.etTimeStart.text = selectedTime
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePicker.show()
            }

            etTimeEnd.setOnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val timePicker = TimePickerDialog(
                    requireContext(),
                    R.style.CustomTimePickerDialogTheme,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                        binding.etTimeEnd.text = selectedTime
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

            swRemindMe.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    swRemindMe.backgroundTintList = resources.getColorStateList(R.color.blue_800)
                else
                    swRemindMe.backgroundTintList = resources.getColorStateList(R.color.grey_200)
            }
        }
    }
}