package com.kiki.storyapp.Main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kiki.storyapp.Login.LoginViewModel
import com.kiki.storyapp.Model.userPreference
import com.kiki.storyapp.Register.RegisterViewModel

class ViewModelFactory(private val pref: userPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java)->{
                RegisterViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}