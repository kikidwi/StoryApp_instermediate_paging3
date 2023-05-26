package com.kiki.storyapp.ListPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kiki.storyapp.Data.StoryRepository
import com.kiki.storyapp.Response.ListStory

class ListPagingViewModel(storyRepository: StoryRepository) : ViewModel() {

    val list: LiveData<PagingData<ListStory>> =
        storyRepository.getStory().cachedIn(viewModelScope)

}

class ViewModelFactoryPaging(private val token : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListPagingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListPagingViewModel(Injection.provideRepository(token)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}