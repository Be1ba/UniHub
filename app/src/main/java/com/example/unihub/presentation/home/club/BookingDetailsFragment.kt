package com.example.unihub.presentation.home.club

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.unihub.R
import com.example.unihub.databinding.FragmentBookingDetailsBinding
import com.example.unihub.presentation.notifications.NotificationsViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.getValue

class BookingDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBookingDetailsBinding
    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private val args: BookingDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookingDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        notificationsViewModel.getTickets(sharedProvider.getToken())

        notificationsViewModel.getTicketsResponse.observe(viewLifecycleOwner) { response ->
            val ticket = response.find { it.id == args.id }
            if (ticket != null) {
                binding.run {
                    tvSender.text = sharedProvider.getName() + " " + sharedProvider.getSurname()
                    tvCreateDate.text = "Date of create: " + formatDatePlusDays(ticket.createdAt)
                    etStatus.setText(ticket.status)
                    tvPersonNumber.text = "Number of Person: " + ticket.numberOfPersons.toString()
                    tvComment.text = ticket.poster.eventTitle + " " + ticket.poster.location
                }
            }
        }
    }

    fun showBase64Image(base64String: String, imageView: ImageView) {
        try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDatePlusDays(isoDate: String, daysToAdd: Long = 7): String {
        val zonedDateTime = ZonedDateTime.parse(isoDate)
            .plusDays(daysToAdd)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
        return zonedDateTime.format(formatter)
    }
}