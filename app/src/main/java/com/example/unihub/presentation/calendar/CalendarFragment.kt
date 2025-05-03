package com.example.unihub.presentation.calendar

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCalendarBinding
import com.example.unihub.utils.provideNavigationHost
import java.util.Calendar

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)

        val calendar = Calendar.getInstance()

        var yearSelected = calendar.get(Calendar.YEAR);
        var monthSelected = calendar.get(Calendar.MONTH);
        var daySelected = calendar.get(Calendar.DAY_OF_MONTH);
        setMonthAndYear(monthSelected, yearSelected)


        binding.run {
            llYear.setOnClickListener {
                val datePickerDialog = DatePickerDialog(requireContext(), { _: DatePicker, selectedYear: Int, selectedMonth: Int, daySelected: Int ->
                    calendar.set(Calendar.YEAR, selectedYear)
                    calendar.set(Calendar.DAY_OF_MONTH, daySelected)
                    calendar.set(Calendar.MONTH, selectedMonth)
                    viewCalendar.date = calendar.timeInMillis

                    setMonthAndYear(selectedMonth, selectedYear)
                }, yearSelected, monthSelected, daySelected)
                datePickerDialog.show()
            }

            viewCalendar.setOnDateChangeListener { view, year, month, dayOfMonth ->

            }

            btnPlus.setOnClickListener {
                findNavController().navigate(CalendarFragmentDirections.actionCalendarFragmentToAddEventFragment())
            }
        }

        addEventCard("8:00 AM")
        addEventCard("12:00 AM")
    }

    private fun setMonthAndYear(month: Int, year: Int) {
        binding.tvYear.text = "${getMonthName(month)} $year"
    }

    private fun getMonthName(month: Int): String {
        return when (month) {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            11 -> "December"
            else -> {
                ""
            }
        }
    }


    fun addEventCard(time: String) {
        val eventCardLayout = LayoutInflater.from(requireContext()).inflate(R.layout.item_calendar_card, binding.clEventsTime, false)

        val icEvent = eventCardLayout.findViewById<ImageButton>(R.id.icEvent)
        val tvEventName = eventCardLayout.findViewById<TextView>(R.id.tvEventName)
        val tvEventTime = eventCardLayout.findViewById<TextView>(R.id.tvEventTime)
        val tvEventDuration = eventCardLayout.findViewById<TextView>(R.id.tvEventDuration)

        tvEventName.text = "Sample Event"
        tvEventTime.text = time
        tvEventDuration.text = "1 hours"

        eventCardLayout.setOnClickListener {
            findNavController().navigate(CalendarFragmentDirections.actionCalendarFragmentToEventsFragment(1))
        }

        if (time == "8:00 AM") {
            binding.llTime8am.addView(eventCardLayout)
        } else if (time == "9:00 AM") {
            binding.llTime9am.addView(eventCardLayout)
        } else if (time == "10:00 AM") {
            binding.llTime10am.addView(eventCardLayout)
        } else if (time == "11:00 AM") {
            binding.llTime11am.addView(eventCardLayout)
        } else if (time == "12:00 AM") {
            binding.llTime12am.addView(eventCardLayout)
        } else if (time == "1:00 PM") {
            binding.llTime1pm.addView(eventCardLayout)
        } else if (time == "2:00 PM") {
            binding.llTime2pm.addView(eventCardLayout)
        } else if (time == "3:00 PM") {
            binding.llTime3pm.addView(eventCardLayout)
        } else if (time == "4:00 PM") {
            binding.llTime4pm.addView(eventCardLayout)
        } else if (time == "5:00 PM") {
            binding.llTime5pm.addView(eventCardLayout)
        } else if (time == "6:00 PM") {
            binding.llTime6pm.addView(eventCardLayout)
        } else if (time == "7:00 PM") {
            binding.llTime7pm.addView(eventCardLayout)
        } else if (time == "8:00 PM") {
            binding.llTime8pm.addView(eventCardLayout)
        } else {
            return
        }
    }
}