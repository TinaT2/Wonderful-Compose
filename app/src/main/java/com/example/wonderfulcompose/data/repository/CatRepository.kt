package com.example.wonderfulcompose.data.repository

import com.example.wonderfulcompose.data.fake.catList
import com.example.wonderfulcompose.data.models.CatPresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatRepository {
    fun loadCats(isFavorite: Boolean): Flow<List<CatPresenter>> {
        return flow { catList }
    }

    fun AddToFavorite(cat: CatPresenter) {
        TODO("Not yet implemented")
    }

    fun RemoveFromFavorite(cat: CatPresenter) {
        TODO("Not yet implemented")
    }
}