package com.kiki.storyapp.Register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.kiki.storyapp.Login.LoginActivity
import com.kiki.storyapp.Model.userPreference
import com.kiki.storyapp.R
import com.kiki.storyapp.Main.ViewModelFactory
import com.kiki.storyapp.databinding.ActivityLoginBinding
import com.kiki.storyapp.databinding.ActivityRegisterBinding
import java.lang.Error
import java.lang.ref.WeakReference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
var weakReference: WeakReference<ActivityRegisterBinding>? = null

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel


    companion object {
        fun isErrorPassword(isError: Boolean) {
            val binding = weakReference?.get()
            binding?.layoutPassword?.isEndIconVisible = !isError
            binding?.btnRegister?.isEnabled = !isError

        }

        var isError = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(userPreference.getInstance(dataStore))
        )[RegisterViewModel::class.java]
    }

    private fun setupAction() {

        registerViewModel.Loading.observe(this){
            setLoading(it)
        }

        registerViewModel.msg.observe(this) {
            AlertDialog.Builder(this@RegisterActivity).apply {
                setTitle(if (isError) "Error" else "Yeah!")
                setMessage(it)
                setPositiveButton("OK") { _, _ ->
                    if (!isError) finish()
                }
                create()
                show()
            }
        }
        binding.btnRegister.setOnClickListener {


            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.etName.error = "Masukkan nama anda"
                }
                email.isEmpty() -> {
                    binding.etEmail.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.etPassword.error = "Masukkan password"
                }
                else -> {
                    registerViewModel.register(name, email, password)
                    registerViewModel.isError.observe(this) {
                        if (!it) registerSucces() else registerError()
                    }

                }
            }
        }

        binding.tvLogin.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    fun setLoading(isLoading : Boolean){
        if (isLoading){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }

    fun registerSucces() {
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage(
                R.string.register_succes_msg
            )
            setPositiveButton("OK") { _, _ ->
                finish()
            }
            create()
            show()
        }
    }

    fun registerError() {
        AlertDialog.Builder(this).apply {
            setTitle("ERROR!")
            setMessage(
                R.string.register_error_msg
            )
            setPositiveButton("OK") { _, _ ->

            }
            create()
            show()
        }
    }

    private fun playAnimation() {

        val Welcome1 = ObjectAnimator.ofFloat(binding.tvWelcome , View.ALPHA, 1f).setDuration(300)
        val etName = ObjectAnimator.ofFloat(binding.layoutNama, View.ALPHA, 1f).setDuration(300)
        val etEmail = ObjectAnimator.ofFloat(binding.layoutEmail, View.ALPHA, 1f).setDuration(300)
        val etPassword = ObjectAnimator.ofFloat(binding.layoutPassword, View.ALPHA, 1f).setDuration(300)
        val signup = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(300)
        val text1 = ObjectAnimator.ofFloat(binding.tvKetLogin, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(Welcome1, etName, etEmail, etPassword, signup, text1, login)
            startDelay = 500
        }.start()
    }
}