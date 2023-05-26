//package com.kiki.storyapp.UnitTest
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.PagingData
//import com.kiki.storyapp.Data.StoryPagingSource
//import com.kiki.storyapp.Data.StoryRepository
//import com.kiki.storyapp.DataDummy
//import com.kiki.storyapp.MainDispatcherRule
//import com.kiki.storyapp.Response.ListStory
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.*
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.junit.MockitoJUnitRunner
//
//@ExperimentalCoroutinesApi
//@RunWith(MockitoJUnitRunner::class)
//class MainViewModelTest{
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//    @get:Rule
//    val mainDispatcherRules = MainDispatcherRule()
//    @Mock
//    private lateinit var quoteRepository: StoryRepository
//
//    @Test
//    fun `when Get story Should Not Null and Return Data`() = runTest {
//        val dummyStory = DataDummy.generateDummyStoriesResponse()
//        val data: PagingData<ListStory> = StoryPagingSource.snapshot(dummyQuote)
//        val expectedQuote = MutableLiveData<PagingData<ListStory>>()
//        expectedQuote.value = data
//        Mockito.`when`(quoteRepository.getQuote()).thenReturn(expectedQuote)
//
//        val mainViewModel = MainViewModel(quoteRepository)
//        val actualQuote: PagingData<QuoteResponseItem> = mainViewModel.quote.getOrAwaitValue()
//
//    }
//
//}