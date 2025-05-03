package com.example.unihub.presentation.home.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.R
import com.example.unihub.data.model.club.ClubsResponseItem
import com.example.unihub.databinding.ItemRecCardBinding
import com.example.unihub.utils.RcViewItemClickIdCallback

open class RecCardAdapter: RecyclerView.Adapter<RecCardAdapter.ClubsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ClubsResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ClubsResponseItem,
            newItem: ClubsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ClubsResponseItem,
            newItem: ClubsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<ClubsResponseItem>) {
        differ.submitList(list)
    }

    var listenerClickAtItem: RcViewItemClickIdCallback? = null
    fun setOnFollowClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickAtItem = listener
    }

    inner class ClubsViewHolder(
        var binding: ItemRecCardBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(clubs: ClubsResponseItem) {
            binding.run {
                tvRecClub.text = clubs.name
                var isFollowed = false

                ivRecClub.setImageResource(R.drawable.puzzle_club)

                if (isFollowed)
                    btnFollow.text = "Unfollow"
                else
                    btnFollow.text = "Follow"

                btnFollow.setOnClickListener {
                    if (isFollowed)
                        btnFollow.text = "Unfollow"
                    else
                        btnFollow.text = "Follow"
                    isFollowed = !isFollowed
                    listenerClickAtItem?.onClick(clubs.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClubsViewHolder {
        return ClubsViewHolder(
            ItemRecCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ClubsViewHolder,
        position: Int
    ) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}