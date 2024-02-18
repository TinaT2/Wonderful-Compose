package com.example.wonderfulcompose.ui.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wonderfulcompose.data.CatAPI
import com.example.wonderfulcompose.data.models.CatPresenter

class CatsPagingSource(
    val api: CatAPI,
    val pageSize: Int,
    val query: String? = null
) : PagingSource<Int, CatPresenter>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, CatPresenter> {
        return try {
            // Start refresh at page 1 if undefined.
            val pageNumber = params.key ?: 1
            val response = api.getCat(pageNumber = pageNumber, pageSize = pageSize)
            //(pageNumber = nextPageNumber)
            LoadResult.Page(
                data = response,
                prevKey = null, // Only paging forward.
                nextKey = pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatPresenter>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}