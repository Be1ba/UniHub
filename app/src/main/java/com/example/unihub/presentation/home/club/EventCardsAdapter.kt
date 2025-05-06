package com.example.unihub.presentation.home.club

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.data.model.club.ClubEventsResponseItem
import com.example.unihub.data.model.poster.PostersByClubResponse
import com.example.unihub.data.model.poster.PostersByClubResponseItem
import com.example.unihub.databinding.ItemEventCardBinding
import com.example.unihub.utils.RcViewItemClickIdCallback

open class EventCardsAdapter: RecyclerView.Adapter<EventCardsAdapter.EventsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PostersByClubResponseItem>() {
        override fun areItemsTheSame(
            oldItem: PostersByClubResponseItem,
            newItem: PostersByClubResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PostersByClubResponseItem,
            newItem: PostersByClubResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<PostersByClubResponseItem>) {
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
        fun onBind(event: PostersByClubResponseItem) {
            binding.run {
                tvEventName.text = event.eventTitle
                tvDescription.text = event.description
                tvDate.text = event.eventDate
                tvTime.text = event.time
                tvLocation.text = event.location

                showBase64Image(event.image, ivPostImage)

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

    fun showBase64Image(base64String: String, imageView: ImageView) {
        try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}