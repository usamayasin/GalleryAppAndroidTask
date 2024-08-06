package com.example.avrioctask.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avrioctask.data.model.Album
import com.example.avrioctask.data.model.ContentState
import com.example.avrioctask.data.model.DataState
import com.example.avrioctask.data.model.ErrorState
import com.example.avrioctask.data.model.LoadingState
import com.example.avrioctask.data.model.UIState
import com.example.avrioctask.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val _albumsList = MutableLiveData<List<Album>>()
    val albumsLiveData: LiveData<List<Album>> = _albumsList

    private val _uiState = MutableLiveData<UIState>()
    val uiStateLiveData: LiveData<UIState> = _uiState

    fun fetchAlbums() {
        _uiState.postValue(LoadingState)
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllAlbums().collect { dataState ->
                withContext(Dispatchers.Main) {
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.value = ContentState
                            dataState.data?.let {
                                _albumsList.value = it
                            }
                        }
                        is DataState.Error -> {
                            _uiState.value = ErrorState(dataState.error.message)
                        }
                    }
                }
            }
        }
    }

}
