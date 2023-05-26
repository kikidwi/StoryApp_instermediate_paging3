package com.kiki.storyapp.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kiki.storyapp.Api.ApiConfig
import com.kiki.storyapp.Model.userModel
import com.kiki.storyapp.Model.userPreference
import com.kiki.storyapp.Response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: userPreference) : ViewModel() {

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private val _Loading = MutableLiveData<Boolean>()
    val Loading : LiveData<Boolean> = _Loading


    fun getUser(): LiveData<userModel> {
        return pref.getUser().asLiveData()
    }

    fun login(token: String) {
        viewModelScope.launch {
            pref.login(token)
        }
    }

    fun Auth(email: String, password: String) {
        _Loading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                _msg.value = t.message.toString()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _Loading.value = false
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _msg.value = "Login Success"
                        viewModelScope.launch {
                            if (getUser().value == null) pref.saveUser(
                                userModel(
                                    responseBody.loginResult.userId,
                                    responseBody.loginResult.name,
                                    true,
                                    responseBody.loginResult.token
                                )
                            )
                            pref.login(responseBody.loginResult.token)
                        }
                    } else {
                        _msg.value = responseBody?.message
                    }
                } else {
                    _Loading.value = false
                    val responseBody = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        LoginResponse::class.java
                    )
                    _msg.value = responseBody.message
                }
            }
        })
    }


}