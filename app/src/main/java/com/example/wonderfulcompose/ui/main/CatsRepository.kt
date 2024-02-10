package com.example.wonderfulcompose.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.wonderfulcompose.data.fake.catList
import com.example.wonderfulcompose.data.models.CatPresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatsRepository @Inject constructor(){
    fun getCats(dispatcher: CoroutineDispatcher = Dispatchers.IO) =
    Pager(
           // Configure how data is loaded by passing additional properties to
           // PagingConfig, such as prefetchDistance.
           PagingConfig(pageSize = 20)
       ) {
           CatsPagingSource()
       }.flow
}