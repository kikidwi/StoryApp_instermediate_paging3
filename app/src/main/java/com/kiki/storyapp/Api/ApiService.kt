package com.kiki.storyapp.Api

import com.kiki.storyapp.Response.LoginResponse
import com.kiki.storyapp.Response.RegisterResponse
import com.kiki.storyapp.Response.StoryResponse
import com.kiki.storyapp.Response.UploadStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("stories")
    suspend fun getStory(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

    @Multipart
    @POST("stories")
    fun addStory(
        @Header("Authorization") authorization: String,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<UploadStoryResponse>

    @GET("stories?location=1")
    fun getStoryLocation(
        @Header("Authorization") authorization: String
    ): Call<StoryResponse>
}