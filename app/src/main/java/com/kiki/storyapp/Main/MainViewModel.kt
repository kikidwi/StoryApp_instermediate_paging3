package com.kiki.storyapp.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kiki.storyapp.Api.ApiConfig
import com.kiki.storyapp.Data.StoryRepository
import com.kiki.storyapp.Model.userModel
import com.kiki.storyapp.Model.userPreference
import com.kiki.storyapp.Response.ListStory
import com.kiki.storyapp.Response.StoryResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: userPreference) : ViewModel(){
    private val _listStory = MutableLiveData<List<ListStory>>()
    val listStory: LiveData<List<ListStory>> = _listStory

    private var _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val _locationStory = MutableLiveData<List<ListStory>>()
    val locationStory: LiveData<List<ListStory>> = _locationStory


    //val story: LiveData<PagingData<ListStory>> = StoryRepository.getStory().cachedIn()

    fun getUser(): LiveData<userModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

//   fun getStory(token: String) {
//        ApiConfig.getApiService()
//            .getStory("Bearer $token")
//            .enqueue(object : Callback<StoryResponse> {
//                override fun onResponse(
//                    call: Call<StoryResponse>,
//                    response: Response<StoryResponse>
//                ) {
//                    if (response.isSuccessful){
//                        val responseBody = response.body()
//                        if (responseBody != null) {
//                            _listStory.value = responseBody.listStory
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                    Log.d("Faillure" , t.message.toString())
//                }
//
//            })
//    }

    fun getStoryLocation(token: String) {
        val client = ApiConfig.getApiService().getStoryLocation("Bearer $token")
        client.enqueue(object : Callback<StoryResponse> {

            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _locationStory.value = responseBody.listStory
                        _errorMsg.value = responseBody.listStory.toString()
                    }
                    _errorMsg.value = responseBody?.message.toString()
                }
            }
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                _errorMsg.value = t.message.toString()
            }
        })
    }

}