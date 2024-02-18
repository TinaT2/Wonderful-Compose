package com.example.wonderfulcompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wonderfulcompose.data.models.CatPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<PagingData<CatPresenter>>(PagingData.empty())
    val catsFlow :StateFlow<PagingData<CatPresenter>>  = _catsFlow

    fun getCats() {
        viewModelScope.launch {
            catsRepository.getCats().cachedIn(viewModelScope).collect {
                _catsFlow.value = it
            }
        }


    }
}