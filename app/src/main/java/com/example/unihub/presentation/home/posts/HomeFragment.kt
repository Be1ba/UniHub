package com.example.unihub.presentation.home.posts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.unihub.R
import com.example.unihub.data.model.club.ClubsResponseItem
import com.example.unihub.data.model.club.Head
import com.example.unihub.data.model.post.Club
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.data.model.post.User
import com.example.unihub.databinding.FragmentHomeBinding
import com.example.unihub.databinding.ItemPostCardBinding
import com.example.unihub.utils.CustomDividerItemDecoration
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.SpacesItemDecoration
import com.example.unihub.utils.provideNavigationHost
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    internal lateinit var postBinding: ItemPostCardBinding
    private val postsViewModel: PostsViewModel by viewModels()
    internal lateinit var firstPost: PostsResponseItem
    private val clubsViewModel: ClubsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)
        val sharedProvider = SharedProvider(requireContext())
        provideNavigationHost()?.setupBottomNavForRole(sharedProvider.getRole().lowercase().contains("admin"))

        if (sharedProvider.getRole().lowercase().contains("super")) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAdminPageFragment())
        }


        postsViewModel.getPostsList(sharedProvider.getToken())
        clubsViewModel.getClubList(sharedProvider.getToken())

        firstPost = PostsResponseItem(
            id = 1,
            club = Club(
                id = 1,
                name = "Sample Club"
            ),
            content = "Sample content",
            createdAt = "2023-01-01T00:00:00Z",
            image = "sample_post_image_url",
            likes = 100,
            title = "Sample Title",
            user = User(
                id = 1,
                name = "John",
                surname = "Doe"
            )
        )

        val postsAdapter = PostsAdapter()

        postsViewModel.getPostListResponse.observe(viewLifecycleOwner) { postList->
            postsAdapter.submitList(postList.filter { it.id != postList[0].id })
            firstPost = postList?.firstOrNull()?:
                PostsResponseItem(
                    id = 1,
                    club = Club(
                        id = 1,
                        name = "Sample Club"
                    ),
                    content = "Sample content",
                    createdAt = "2023-01-01T00:00:00Z",
                    image = "sample_post_image_url",
                    likes = 100,
                    title = "Sample Title",
                    user = User(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    )
                )
            updateFirstPost()
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

        val clubsAdapter = RecCardAdapter()

        clubsViewModel.getClubListResponse.observe(viewLifecycleOwner) { clubList ->
            clubsAdapter.submitList(clubList)
        }

        clubsAdapter.setOnFollowClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToClubPageFragment(id?: 1, ""))
                }
            }
        )

        val verticalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val horizontalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        postBinding = binding.firstPost

        binding.run {
            rvPosts.layoutManager = verticalLinearLayoutManager
            rvPosts.adapter = postsAdapter
            rvPosts.addItemDecoration(
                CustomDividerItemDecoration(
                    getDrawable(
                        requireContext(),
                        R.drawable.divider_1dp
                    )!!
                )
            )

            rvClubs.layoutManager = horizontalLinearLayoutManager
            rvClubs.adapter = clubsAdapter
            val space = resources.getDimensionPixelSize(R.dimen.dp_12)
            rvClubs.addItemDecoration(SpacesItemDecoration(space))

            llSearch.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            }

            etSearch.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            }

            btnSubscriptions.setOnClickListener {
                val color = ContextCompat.getColor(requireContext(), R.color.blue_800)
                val buttonDrawable = DrawableCompat.wrap(btnSubscriptions.background)
                DrawableCompat.setTint(buttonDrawable, color)
                btnSubscriptions.background = buttonDrawable
                btnSubscriptions.setTextColor(ContextCompat.getColor(requireContext(), R.color.white_1000))
                btnSubscriptions.isEnabled = false

                val color1 = ContextCompat.getColor(requireContext(), R.color.white_900)
                val buttonDrawable1 = DrawableCompat.wrap(btnRecommendations.background)
                DrawableCompat.setTint(buttonDrawable1, color1)
                btnRecommendations.background = buttonDrawable1
                btnRecommendations.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_200))
                btnRecommendations.isEnabled = true

                tvWelcomeName.visibility = View.GONE
                llClubs.visibility = View.GONE
                llFirstPost.visibility = View.GONE
                llSearch.visibility = View.GONE
            }

            btnRecommendations.setOnClickListener {
                val color = ContextCompat.getColor(requireContext(), R.color.blue_800)
                val buttonDrawable = DrawableCompat.wrap(btnRecommendations.background)
                DrawableCompat.setTint(buttonDrawable, color)
                btnRecommendations.background = buttonDrawable
                btnRecommendations.setTextColor(ContextCompat.getColor(requireContext(), R.color.white_1000))
                btnRecommendations.isEnabled = false

                val color1 = ContextCompat.getColor(requireContext(), R.color.white_900)
                val buttonDrawable1 = DrawableCompat.wrap(btnSubscriptions.background)
                DrawableCompat.setTint(buttonDrawable1, color1)
                btnSubscriptions.background = buttonDrawable1
                btnSubscriptions.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_200))
                btnSubscriptions.isEnabled = true

                tvWelcomeName.visibility = View.VISIBLE
                llClubs.visibility = View.VISIBLE
                llFirstPost.visibility = View.VISIBLE
                llSearch.visibility = View.VISIBLE
            }
        }
    }
}

@SuppressLint("NewApi")
private fun getTimeAgo(isoTime: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val updatedTime = ZonedDateTime.parse(isoTime, formatter)
    val now = ZonedDateTime.now(ZoneId.of("UTC"))
    val duration = Duration.between(updatedTime, now)

    return when {
        duration.toMinutes() < 1 -> "just now"
        duration.toMinutes() < 60 -> "${duration.toMinutes()} minutes ago"
        duration.toHours() < 24 -> "${duration.toHours()} hours ago"
        duration.toDays() < 7 -> "${duration.toDays()} days ago"
        else -> updatedTime.toLocalDate().toString() // Or format if needed
    }
}

private fun HomeFragment.updateFirstPost() {
    postBinding.run {
        llClubInfo
        tvClubName.text = firstPost.club?.name
        tvTime.text = getTimeAgo(firstPost.createdAt)
        tvPostName.text = firstPost.content
        var isLiked = false

        if (firstPost.image.isEmpty())
            ivPostImage.setImageResource(R.drawable.example_post)
        else
            Glide.with(requireContext())
                .load(firstPost.image)
                .into(ivPostImage)

        if (isLiked)
            btnLike.setImageResource(R.drawable.ic_liked)
        else
            btnLike.setImageResource(R.drawable.ic_unliked)

        btnLike.setOnClickListener {
            if (isLiked)
                btnLike.setImageResource(R.drawable.ic_unliked)
            else
                btnLike.setImageResource(R.drawable.ic_liked)
            isLiked = !isLiked
        }

        tvClubName.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToClubPageFragment(firstPost.club?.id ?: 1, ""))
        }
    }
}
