package com.example.wonderfulcompose.data

import com.example.wonderfulcompose.data.fake.catList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatAPI @Inject constructor() {

    suspend fun getCat(pageNumber: Int, pageSize: Int = 10) = withContext(Dispatchers.IO) {
        if (pageNumber == 1) catList.subList(0, pageSize)
        else catList.subList(pageSize * (pageNumber - 1), pageSize * (pageNumber))

    }
}