package com.example.avrioctask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.avrioctask.data.model.Album
import com.example.avrioctask.databinding.ItemAlbumBinding

class AlbumsAdapter(
    private val onAlbumClick: ((String) -> Unit)? = null
) :
    RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlbumViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class AlbumViewHolder(private val itemBinding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(album: Album) {
            itemBinding.apply {
                data = album
                image.setOnClickListener {
                    onAlbumClick?.let { it -> it(album.name) }
                }
            }
        }
    }


    private val differCallBack = object : DiffUtil.ItemCallback<Album>() {

        override fun areItemsTheSame(
            oldItem: Album,
            newItem: Album
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Album,
            newItem: Album
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)


}