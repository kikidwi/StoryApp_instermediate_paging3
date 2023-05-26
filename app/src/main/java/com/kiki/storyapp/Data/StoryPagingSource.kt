package com.kiki.storyapp.Data

import android.media.session.MediaSession.Token
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kiki.storyapp.Api.ApiService
import com.kiki.storyapp.Model.userPreference
import com.kiki.storyapp.Response.ListStory

class StoryPagingSource(private val apiService: ApiService, private val token: String) : PagingSource<Int, ListStory>() {


    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStory> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStory(
                "Bearer ${token}",
                position,
                params.loadSize
            )
            val dataList = responseData.listStory

            LoadResult.Page(
                data =  dataList,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (dataList.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, ListStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
