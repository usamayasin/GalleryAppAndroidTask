package com.example.avrioctask.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.avrioctask.data.model.MediaItem
import com.example.avrioctask.data.model.MediaType
import com.example.avrioctask.databinding.ItemMediaBinding

class MediaAdapter(
    private val onImageClick: ((Uri) -> Unit)? = null,
    private val onVideoClick: ((Uri) -> Unit)? = null
) :
    RecyclerView.Adapter<MediaAdapter.MediaItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaItemViewHolder {
        val binding = ItemMediaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MediaItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MediaItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class MediaItemViewHolder(private val itemBinding: ItemMediaBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: MediaItem) {
            itemBinding.apply {
                data = item
                image.setOnClickListener {
                    if (item.type == MediaType.IMAGE) {
                        onImageClick?.invoke(item.uri)
                    } else {
                        onVideoClick?.invoke(item.uri)
                    }
                }
            }
        }
    }


    private val differCallBack = object : DiffUtil.ItemCallback<MediaItem>() {

        override fun areItemsTheSame(
            oldItem: MediaItem,
            newItem: MediaItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MediaItem,
            newItem: MediaItem
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)


}