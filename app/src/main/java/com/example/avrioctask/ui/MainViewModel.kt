package com.example.avrioctask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _isGridLayoutManager = MutableLiveData(true)
    val isGridLayoutManager: LiveData<Boolean> = _isGridLayoutManager

    fun toggleLayoutManager() {
        _isGridLayoutManager.value = !_isGridLayoutManager.value!!
    }
}