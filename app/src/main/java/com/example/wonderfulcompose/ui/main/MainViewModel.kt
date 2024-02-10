package com.example.wonderfulcompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val catsRepository: CatsRepository
) : ViewModel() {

    fun getCats() = catsRepository.getCats().cachedIn(viewModelScope)
}