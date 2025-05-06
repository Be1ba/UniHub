package com.example.unihub.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.R
import com.example.unihub.databinding.FragmentUserProfileBinding
import com.example.unihub.presentation.home.posts.HomeFragmentDirections
import com.example.unihub.presentation.home.posts.PostsAdapter
import com.example.unihub.presentation.home.posts.PostsViewModel
import com.example.unihub.presentation.home.posts.RepostBottomSheet
import com.example.unihub.presentation.home.posts.ShareBottomSheet
import com.example.unihub.utils.CustomDividerItemDecoration
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private val postsViewModel: PostsViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)
        val sharedProvider = SharedProvider(requireContext())

        findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToProfileFragment())

        profileViewModel.getUserProfile(sharedProvider.getToken())

        profileViewModel.profileResponse.observe(viewLifecycleOwner) {
            binding.tvUserName.text = it.name + " " + it.surname
        }

        postsViewModel.getMyPostsList(sharedProvider.getToken())
        val postsAdapter = PostsAdapter()

        postsViewModel.getMyPostsListResponse.observe(viewLifecycleOwner) { postList->
            postsAdapter.submitList(postList)
        }

        postsAdapter.setOnLikeClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    postsViewModel.likePost(sharedProvider.getToken(), id?: 1)
                }
            }
        )

        postsAdapter.setOnClubNameClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToClubPageFragment(id?: 1, ""))
                }
            }
        )

        postsAdapter.setOnRepostClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    val repostBottomSheet = RepostBottomSheet(id?:1)
                    repostBottomSheet.show(childFragmentManager, repostBottomSheet.tag)
                }
            }
        )

        postsAdapter.setOnShareClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    val shareBottomSheet = ShareBottomSheet(id?:1)
                    shareBottomSheet.show(childFragmentManager, shareBottomSheet.tag)
                }
            }
        )

        val verticalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.run {
            btnSettings.setOnClickListener {
                findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToSettingsFragment())
            }

            btnEditUser.setOnClickListener {
                findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToProfileFragment())
            }

            btnWrite.setOnClickListener {
                findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToCreateRequestFragment())
            }

            rvReposts.layoutManager = verticalLinearLayoutManager
            rvReposts.adapter = postsAdapter
            rvReposts.addItemDecoration(
                CustomDividerItemDecoration(
                    getDrawable(
                        requireContext(),
                        R.drawable.divider_1dp
                    )!!
                )
            )

            tvReposts.setOnClickListener {
                rvReposts.visibility = View.VISIBLE
                rvReplies.visibility = View.GONE
                idReposts.visibility = View.VISIBLE
                idReplies.visibility = View.INVISIBLE
            }

            tvReplies.setOnClickListener {
                rvReposts.visibility = View.GONE
                rvReplies.visibility = View.VISIBLE
                idReposts.visibility = View.INVISIBLE
                idReplies.visibility = View.VISIBLE
            }
        }
    }
}