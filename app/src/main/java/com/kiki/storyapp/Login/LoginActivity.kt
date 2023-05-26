package com.kiki.storyapp.Login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.kiki.storyapp.CustomView.CustomPasswordEditText
import com.kiki.storyapp.Main.MainActivity
import com.kiki.storyapp.Model.userModel
import com.kiki.storyapp.Model.userPreference
import com.kiki.storyapp.R
import com.kiki.storyapp.Register.RegisterActivity
import com.kiki.storyapp.Main.ViewModelFactory
import com.kiki.storyapp.databinding.ActivityLoginBinding
import java.lang.ref.WeakReference


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
lateinit var weakReference: WeakReference<ActivityLoginBinding>
class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var user: userModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weakReference = WeakReference(binding)
        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(userPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this, { user ->
            this.user = user
        })
    }

    private fun setupAction() {

        loginViewModel.Loading.observe(this){
            setLoading(it)    
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.etEmail.error = "Masukkan email"
                }
                password.isEmpty() -> {

                    binding.etPassword.error = "Masukkan password"
                }
                else -> {
                    loginViewModel.Auth(email, password)
                    loginViewModel.msg.observe(this) {
                        AlertDialog.Builder(this).apply {
                            setTitle(getString(R.string.app_name))
                            setMessage(it)
                            setPositiveButton("OK") { _, _ ->
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    fun setLoading(isLoading : Boolean){
        if (isLoading){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }

    private fun playAnimation() {


        val Welcome1 = ObjectAnimator.ofFloat(binding.tvWelcome , View.ALPHA, 1f).setDuration(300)
        val Welcome2 = ObjectAnimator.ofFloat(binding.tvWelcome2 , View.ALPHA, 1f).setDuration(300)
        val etEmail = ObjectAnimator.ofFloat(binding.layoutEmail, View.ALPHA, 1f).setDuration(300)
        val etPassword = ObjectAnimator.ofFloat(binding.layoutPassword, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(300)
        val text1 = ObjectAnimator.ofFloat(binding.tvKetRegister, View.ALPHA, 1f).setDuration(300)
        val signup = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(Welcome1, Welcome2, etEmail, etPassword, login, text1, signup)
            startDelay = 500
        }.start()
    }


    companion object {
        fun isErrorPassword(isError: Boolean) {
            val binding = weakReference.get()
            binding?.layoutPassword?.isEndIconVisible = !isError
            binding?.btnLogin?.isEnabled = !isError
        }
    }
}