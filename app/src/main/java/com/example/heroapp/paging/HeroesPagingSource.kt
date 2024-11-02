package com.example.heroapp.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.heroapp.network.MarvelApi
import com.example.heroapp.network.dataClasses.CharacterRestModel


class HeroesPagingSource(private val apiService: MarvelApi) :
    PagingSource<Int, CharacterRestModel>() {

    // Add a counter variable to keep track of the number of times the `load()` function is called
    private var loadCounter = 0

    override fun getRefreshKey(state: PagingState<Int, CharacterRestModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(
                anchorPosition
            )
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,
            CharacterRestModel> {
        loadCounter++
      //  Log.i("Pagination", "getCharacters() called $loadCounter times") // Log the load counter
        return try {
            val page = params.key ?: 0
            val offset = page * PAGE_SIZE
            val response = apiService.Marvel_Api_Service.getAllCharacters(offset = offset, 10)
         //   Log.i("Pagination", "Offset: $offset")
          //  Log.i("response",response.body().toString())
            val nextKey =
                if (offset >= (response.body()?.data?.total ?: 0)) null
                else page + 1

            // Log.i("Pagination total", "total: " + response.body()?.data?.total.toString())
            return LoadResult.Page(
                data = response.body()?.data?.results ?: emptyList(),
                prevKey = null, // Only paging forward.nextKey
                nextKey = nextKey
            )
        } catch (e: Exception) {

            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}