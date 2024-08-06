package com.example.avrioctask.data.model

data class Album(
    var id: String = "0",
    var name: String = "",
    var mediaCount: Int = 0,
    var mediaItems: MutableList<MediaItem> = mutableListOf()
)