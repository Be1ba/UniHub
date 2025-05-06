package com.example.unihub.presentation.home.posts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unihub.R
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.databinding.ItemPostCardBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

open class PostsAdapter: RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PostsResponseItem>() {
        override fun areItemsTheSame(
            oldItem: PostsResponseItem,
            newItem: PostsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PostsResponseItem,
            newItem: PostsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<PostsResponseItem>) {
        differ.submitList(list)
    }

    var listenerClickLike: RcViewItemClickIdCallback? = null
    fun setOnLikeClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickLike = listener
    }

    var listenerClickClubName: RcViewItemClickIdCallback? = null
    fun setOnClubNameClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickClubName = listener
    }

    var listenerClickRepost: RcViewItemClickIdCallback? = null
    fun setOnRepostClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickRepost = listener
    }

    var listenerClickShare: RcViewItemClickIdCallback? = null
    fun setOnShareClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickShare = listener
    }

    inner class PostsViewHolder(
        var binding: ItemPostCardBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(posts: PostsResponseItem) {
            binding.run {
                tvClubName.text = posts.club?.name
                tvTime.text = getTimeAgo(posts.createdAt)
                tvPostName.text = posts.content
                var isLiked = false

                if (posts.image.isEmpty())
                    ivPostImage.setImageResource(R.drawable.example_post)
                else
                    Glide.with(itemView.context)
                        .load(posts.image)
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
                    listenerClickLike?.onClick(posts.id)
                }

                tvClubName.setOnClickListener {
                    listenerClickClubName?.onClick(posts.club?.id)
                }

                btnRepost.setOnClickListener {
                    listenerClickRepost?.onClick(posts.id)
                }

                btnSend.setOnClickListener {
                    listenerClickShare?.onClick(posts.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostsViewHolder {
        return PostsViewHolder(
            ItemPostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PostsViewHolder,
        position: Int
    ) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("NewApi")
    fun getTimeAgo(isoTime: String): String {
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
}