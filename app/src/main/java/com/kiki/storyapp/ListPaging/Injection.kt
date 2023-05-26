package com.kiki.storyapp.ListPaging

import com.kiki.storyapp.Api.ApiConfig
import com.kiki.storyapp.Data.StoryRepository

object Injection {
    fun provideRepository(token : String): StoryRepository {
        val apiService= ApiConfig.getApiService()
        return StoryRepository(apiService, token)
    }

}