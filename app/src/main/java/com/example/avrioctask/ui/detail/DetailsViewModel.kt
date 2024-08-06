package com.example.avrioctask.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avrioctask.data.model.DataState
import com.example.avrioctask.data.model.MediaItem
import com.example.avrioctask.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val _mediaList = MutableLiveData<List<MediaItem>>()
    val mediaLiveData: LiveData<List<MediaItem>> = _mediaList

    fun fetchMediaItems(albumId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMediaItems(albumId).collect { dataState ->
                withContext(Dispatchers.Main) {
                    when (dataState) {
                        is DataState.Success -> {
                            dataState.data?.let {
                                _mediaList.value = it
                            }
                        }
                        is DataState.Error -> {
                        }
                    }
                }
            }
        }
    }

}
