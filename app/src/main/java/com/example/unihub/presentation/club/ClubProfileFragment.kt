package com.example.unihub.presentation.club

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.R
import com.example.unihub.databinding.FragmentClubProfileBinding
import com.example.unihub.presentation.home.club.ClubRatingsAdapter
import com.example.unihub.presentation.home.club.EventCardsAdapter
import com.example.unihub.presentation.home.posts.PostsAdapter
import com.example.unihub.presentation.profile.ProfileViewModel
import com.example.unihub.utils.CustomDividerItemDecoration
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.time.ZonedDateTime

class ClubProfileFragment : Fragment() {

    private lateinit var binding: FragmentClubProfileBinding
    private val clubViewModel: ClubViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClubProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)
        val sharedProvider = SharedProvider(requireContext())

        clubViewModel.getHeadProfile(sharedProvider.getToken())

        var clubMotto = ""
        var clubInfo = ""
        var headName = ""

        clubViewModel.profileResponse.observe(viewLifecycleOwner) {
            clubViewModel.getClubDetails(sharedProvider.getToken(), it.clubInfo?.id?: 0)
            clubViewModel.getPosterByClub(sharedProvider.getToken(), it.clubInfo?.id?: 0)
            Log.d("TAG", "onViewCreated: ${it.clubInfo}")
        }

        clubViewModel.getMyPosts(sharedProvider.getToken())

        val postsAdapter = PostsAdapter()

        clubViewModel.clubResponse.observe(viewLifecycleOwner) {
            binding.run {
                tvClubName.text = it.name
                tvClubTime.text = ZonedDateTime.parse(it.createdAt).year.toString()

                clubMotto = it.goal
                clubInfo = it.description
                headName = it.head.name + " " + it.head.surname
            }
            Log.d("TAG", "onViewCreated: ${it.head.name} ${it.head.surname}")
        }

        clubViewModel.myPostsResponse.observe(viewLifecycleOwner) { postList ->
            postsAdapter.submitList(postList)
        }

        postsAdapter.setOnLikeClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {

                }
            }
        )

        postsAdapter.setOnClubNameClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {

                }
            }
        )

        val eventsAdapter = EventCardsAdapter()

        clubViewModel.postersByClubResponse.observe(viewLifecycleOwner) {
            eventsAdapter.submitList(it)
        }

        eventsAdapter.setOnCardClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {

                }
            }
        )

        val ratingsAdapter = ClubRatingsAdapter()

        val verticalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.run {
            btnSettings.setOnClickListener {
                findNavController().navigate(ClubProfileFragmentDirections.actionClubProfileFragmentToSettingsFragment())
            }

            btnEditUser.setOnClickListener {
                findNavController().navigate(ClubProfileFragmentDirections.actionClubProfileFragmentToProfileFragment())
            }

            btnWrite.setOnClickListener {
                findNavController().navigate(ClubProfileFragmentDirections.actionClubProfileFragmentToCreateRequestFragment())
            }

            tvAll.setOnClickListener {
                idAll.visibility = View.VISIBLE
                idBooking.visibility = View.INVISIBLE
//                idRating.visibility = View.INVISIBLE

                rvPosts.visibility = View.VISIBLE
                rvEvents.visibility = View.GONE
//                llRatings.visibility = View.GONE
            }

            tvBooking.setOnClickListener {
                idAll.visibility = View.INVISIBLE
                idBooking.visibility = View.VISIBLE
//                idRating.visibility = View.INVISIBLE

                rvPosts.visibility = View.GONE
                rvEvents.visibility = View.VISIBLE
//                llRatings.visibility = View.GONE
            }

            tvRating.setOnClickListener {
                idAll.visibility = View.INVISIBLE
                idBooking.visibility = View.INVISIBLE
//                idRating.visibility = View.VISIBLE

                rvPosts.visibility = View.GONE
                rvEvents.visibility = View.GONE
//                llRatings.visibility = View.VISIBLE
            }

            llClubMoreInfo.setOnClickListener {
                showCustomDialogBox(clubMotto, clubInfo, headName)
            }

            rvPosts.layoutManager = verticalLinearLayoutManager
            rvPosts.adapter = postsAdapter
            rvPosts.addItemDecoration(
                CustomDividerItemDecoration(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.divider_1dp
                    )!!
                )
            )

            rvEvents.adapter = eventsAdapter

            rvRatings.adapter = ratingsAdapter
        }
    }

    private fun showCustomDialogBox(clubMotto: String, clubInfo: String, headName: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_detailed_info)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        val btnClose: ImageButton = dialog.findViewById(R.id.btnClose)
        val tvClubMotto: TextView = dialog.findViewById(R.id.tvClubMotto)
        val tvClubInfo: TextView = dialog.findViewById(R.id.tvClubInfo)
        val tvHeadName: TextView = dialog.findViewById(R.id.tvHeadName)

        tvClubInfo.text = clubInfo
        tvClubMotto.text = clubMotto
        tvHeadName.text = headName

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}