package com.kiki.storyapp.Model

data class userModel(
    val uId: String,
    val name: String,
    val isLogin: Boolean,
    val token: String = ""
)