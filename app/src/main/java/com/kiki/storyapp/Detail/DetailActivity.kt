package com.kiki.storyapp.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kiki.storyapp.R
import com.kiki.storyapp.Response.ListStory
import com.kiki.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    companion object {
        const val EXTRA_URL = "url"
        const val EXTRA_DESC = "desc"
        const val EXTRA_NAME = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.list_story_page)
        val name = intent.getStringExtra(EXTRA_NAME)
        val description = intent.getStringExtra(EXTRA_DESC)
        val photoUrl = intent.getStringExtra(EXTRA_URL)

        Glide.with(this)
            .load(photoUrl)
            .into(binding.ivPhoto)
        binding.tvNameUser.text = name
        binding.tvDesc.text = description

    }
}