package com.example.avrioctask.data.repository

import com.example.avrioctask.data.model.Album
import com.example.avrioctask.data.model.DataState
import com.example.avrioctask.data.model.MediaItem
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun getAllAlbums(): Flow<DataState<List<Album>>>
    suspend fun getMediaItems(albumId: String): Flow<DataState<List<MediaItem>>>
}