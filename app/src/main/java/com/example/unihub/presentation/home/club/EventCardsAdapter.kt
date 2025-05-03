package com.example.unihub.presentation.home.club

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.data.model.club.ClubEventsResponseItem
import com.example.unihub.databinding.ItemEventCardBinding
import com.example.unihub.utils.RcViewItemClickIdCallback

open class EventCardsAdapter: RecyclerView.Adapter<EventCardsAdapter.EventsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ClubEventsResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ClubEventsResponseItem,
            newItem: ClubEventsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ClubEventsResponseItem,
            newItem: ClubEventsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<ClubEventsResponseItem>) {
        differ.submitList(list)
    }

    var listenerClickCard: RcViewItemClickIdCallback? = null
    fun setOnCardClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickCard = listener
    }

    inner class EventsViewHolder(
        var binding: ItemEventCardBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(event: ClubEventsResponseItem) {
            binding.run {
                tvEventName.text = event.eventName
                tvDescription.text = event.shortDescription
                tvDate.text = event.eventDate

//                tvSeats.text = event.location

//                Glide.with(itemView.context)
//                    .load(event.image)
//                    .into(ivPostImage)

                root.setOnClickListener {
                    listenerClickCard?.onClick(event.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsViewHolder {
        return EventsViewHolder(
            ItemEventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: EventsViewHolder,
        position: Int
    ) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}