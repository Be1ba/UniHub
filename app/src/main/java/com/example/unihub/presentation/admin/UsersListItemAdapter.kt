package com.example.unihub.presentation.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.databinding.ItemRequestBinding
import com.example.unihub.databinding.ItemUserAdminBinding
import com.example.unihub.utils.RcViewItemClickIdStringCallback

open class UsersListItemAdapter: RecyclerView.Adapter<UsersListItemAdapter.RequestsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<String>) {
        differ.submitList(list)
    }

    var listenerClickUserName: RcViewItemClickIdStringCallback? = null
    fun setOnUserNameClickListener(listener: RcViewItemClickIdStringCallback) {
        this.listenerClickUserName = listener
    }

    var listenerClickDelete: RcViewItemClickIdStringCallback? = null
    fun setOnDeleteClickListener(listener: RcViewItemClickIdStringCallback) {
        this.listenerClickDelete = listener
    }

    inner class RequestsViewHolder(
        var binding: ItemUserAdminBinding
    ) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun onBind(item: String) {
            binding.run {
                tvUserName.text = item

                tvUserName.setOnClickListener {
                    listenerClickUserName?.onClick(item)
                }

                ibDelete.setOnClickListener {
                    listenerClickDelete?.onClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestsViewHolder {
        return RequestsViewHolder(
            ItemUserAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RequestsViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}