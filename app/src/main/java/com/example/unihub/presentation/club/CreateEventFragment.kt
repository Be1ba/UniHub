package com.example.unihub.presentation.club

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreateEventBinding
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.util.Calendar
import kotlin.getValue

class CreateEventFragment : Fragment() {

    private lateinit var binding: FragmentCreateEventBinding
    private val headViewModel: HeadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        binding.run {
            btnBack.setOnClickListener {
                findNavController().navigate(CreateEventFragmentDirections.actionCreateEventFragmentToCreateNewPostFragment())
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

            btnSend.setOnClickListener {
                val clubHead = etHead.text.toString()
                val comment = etComment.text.toString()
                val eventDate = etDate.text.toString()
                val eventName = etEventName.text.toString()
                val goal = etGoal.text.toString()
                val location = etLocation.text.toString()
                val organizers = etOrganizers.text.toString()
                val phone = etPhone.text.toString()
                val schedule = etShedule.text.toString()
                val shortDescription = etDescription.text.toString()
                val sponsorship = etSponsorship.text.toString()

                headViewModel.createEvent(
                    sharedProvider.getToken(),
                    clubHead = clubHead,
                    comment = comment,
                    eventDate = eventDate,
                    eventName = eventName,
                    goal = goal,
                    location = location,
                    organizers = organizers,
                    phone = phone,
                    schedule = schedule,
                    shortDescription = shortDescription,
                    sponsorship = sponsorship
                )
            }

            headViewModel.createEventResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    findNavController().navigate(CreateEventFragmentDirections.actionCreateEventFragmentToCreateNewPostFragment())
                }
            }

            headViewModel.errorMessage.observe(viewLifecycleOwner) {
                binding.tvError.text = it.message
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }
}