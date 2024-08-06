package com.example.avrioctask.data.model

import android.net.Uri

data class MediaItem(
    val id: Long,
    val uri: Uri,
    val type: MediaType,
    val name: String
)

enum class MediaType {
    IMAGE,
    VIDEO
}