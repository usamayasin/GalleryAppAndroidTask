package com.example.avrioctask.data.repository

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import com.example.avrioctask.data.model.Album
import com.example.avrioctask.data.model.DataState
import com.example.avrioctask.data.model.MediaItem
import com.example.avrioctask.data.model.MediaType
import com.example.avrioctask.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class MediaRepositoryImpl(
    private val contentResolver: ContentResolver
) : MediaRepository {

    var albumCache = listOf<Album>()
    override suspend fun getAllAlbums(): Flow<DataState<List<Album>>> = flow {
        try {
            val cachedAlbums = albumCache.toList()
            if (cachedAlbums.isNotEmpty()) {
                emit(DataState.Success(cachedAlbums))
            } else {
                val freshAlbumsData = getData().first()
                if (freshAlbumsData.isNotEmpty()) {
                    albumCache = freshAlbumsData
                    emit(DataState.Success(freshAlbumsData))
                } else {
                    emit(DataState.Error(DataState.CustomMessages.EmptyData()))
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(DataState.CustomMessages.SomethingWentWrong(e.message.toString())))
        }
    }.catch {
        emit(DataState.Error(DataState.CustomMessages.SomethingWentWrong(it.message.toString())))
    }

    override suspend fun getMediaItems(albumId: String): Flow<DataState<List<MediaItem>>> = flow {
        try {
            val foundAlbum = albumCache.find { it.name == albumId }
            foundAlbum?.let {
                emit(DataState.Success(foundAlbum.mediaItems))
            } ?: run {
                emit(DataState.Error(DataState.CustomMessages.EmptyData()))
            }
        } catch (e: Exception) {
            emit(DataState.Error(DataState.CustomMessages.SomethingWentWrong(e.message.toString())))
        }
    }

    private suspend fun getData(): Flow<List<Album>> = flow {
        emit(withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Files.FileColumns.MEDIA_TYPE
            )

            val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE} IN (?, ?)"
            val selectionArgs = arrayOf(
                MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
                MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
            )

            val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"

            val cursor = contentResolver.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                selectionArgs,
                sortOrder
            ) ?: return@withContext emptyList()

            val albums = mutableListOf<Album>()
            val allImages = mutableListOf<MediaItem>()
            val allVideos = mutableListOf<MediaItem>()

            cursor.use {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)

                val bucketColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)

                val typeColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE)

                while (cursor.moveToNext()) {
                    try {
                        val id = cursor.getLong(idColumn)
                        val name = cursor.getString(nameColumn) ?: ""
                        val bucket = cursor.getString(bucketColumn) ?: ""
                        val type = cursor.getInt(typeColumn)

                        // if unable to find name/bucket skip this iteration
                        if (name.isEmpty() || bucket.isEmpty()) continue

                        val mediaItem = MediaItem(
                            id = id,
                            uri = Uri.withAppendedPath(
                                MediaStore.Files.getContentUri("external"),
                                "" + id
                            ),
                            name = name,
                            type = if (type == 1) MediaType.IMAGE else MediaType.VIDEO
                        )

                        val album = albums.find { it.name == bucket } ?: Album(name = bucket).also {
                            albums.add(it)
                        }
                        album.mediaItems.add(mediaItem)
                        album.mediaCount++

                        if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) {
                            allImages.add(mediaItem)
                        } else if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
                            allVideos.add(mediaItem)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        continue
                    }
                }
            }

            if (allImages.isNotEmpty()) {
                albums.add(
                    Album(
                        name = Constants.ALL_IMAGES_ALBUM_ID,
                        mediaItems = allImages,
                        mediaCount = allImages.size
                    )
                )
            }
            if (allVideos.isNotEmpty()) {
                albums.add(
                    Album(
                        name = Constants.ALL_VIDEOS_ALBUM_ID,
                        mediaItems = allVideos,
                        mediaCount = allVideos.size
                    )
                )
            }
            albums
        })
    }.catch { throwable ->
        throwable.printStackTrace()
        emit(emptyList())
    }
}