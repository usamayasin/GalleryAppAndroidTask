package com.example.avrioctask.data.repository

import android.content.ContentResolver
import com.example.avrioctask.data.model.Album
import com.example.avrioctask.data.model.DataState
import com.example.avrioctask.data.model.MediaItem
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MediaRepositoryImplTest {

    @Mock
    private lateinit var contentResolver: ContentResolver

    private lateinit var mediaRepository: MediaRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mediaRepository = MediaRepositoryImpl(contentResolver)
    }

    @Test
    fun `getAllAlbums returns cached data when available`() = runTest {
        val mockAlbums = listOf(Album("1", "test", 1, mutableListOf<MediaItem>()))
        mediaRepository.albumCache = mockAlbums
        val result = mediaRepository.getAllAlbums().single()
        assertEquals(DataState.Success(mockAlbums).data?.get(0)?.name, result.data?.get(0)?.name)
    }

}