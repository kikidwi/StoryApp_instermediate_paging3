package com.kiki.storyapp.Data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.kiki.storyapp.Api.ApiConfig
import com.kiki.storyapp.Api.ApiService
import com.kiki.storyapp.Response.ListStory

class StoryRepository(private val apiService: ApiService, private val token: String) {

    fun getStory(): LiveData<PagingData<ListStory>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData
    }
}