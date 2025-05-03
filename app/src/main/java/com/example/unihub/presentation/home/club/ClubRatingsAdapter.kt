package com.example.unihub.presentation.home.club

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.data.model.club.ClubsResponseItem
import com.example.unihub.databinding.ItemRatingProgressBinding

open class ClubRatingsAdapter: RecyclerView.Adapter<ClubRatingsAdapter.RatingsViewHolder>() {

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


    inner class RatingsViewHolder(
        var binding: ItemRatingProgressBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(club: ClubsResponseItem) {
            binding.run {
                tvClubName.text = club.name
                club.rating?.let { progressBar.progress = ((it/5)*100).toInt() }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RatingsViewHolder {
        return RatingsViewHolder(
            ItemRatingProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: RatingsViewHolder,
        position: Int
    ) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}