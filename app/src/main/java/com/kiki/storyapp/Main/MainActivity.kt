package com.kiki.storyapp.Main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kiki.storyapp.AddStory.AddStoryActivity
import com.kiki.storyapp.ListPaging.ListPagingViewModel
import com.kiki.storyapp.ListPaging.ListStoryPagingAdapter
import com.kiki.storyapp.ListPaging.LoadingStateAdapter
import com.kiki.storyapp.Login.LoginActivity
import com.kiki.storyapp.Maps.MapsActivity
//import com.kiki.storyapp.Maps.MapsActivity
import com.kiki.storyapp.Model.userPreference
import com.kiki.storyapp.R
import com.kiki.storyapp.ListPaging.ViewModelFactoryPaging
import com.kiki.storyapp.databinding.ActivityMainBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Setting")
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel
    private lateinit var listPagingViewModel: ListPagingViewModel
    private lateinit var listStoryPagingAdapter: ListStoryPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        mainViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(userPreference.getInstance(dataStore))
//        )[MainViewModel::class.java]




        setupViewModel()

//        mainViewModel.listStory.observe(this) {
//            if (it.isEmpty()) {
//                val alertDialogBuilder = AlertDialog.Builder(this)
//                alertDialogBuilder.setTitle(getString(R.string.app_name))
//
//                alertDialogBuilder
//                    .setMessage(getString(R.string.empty_list))
//                    .setCancelable(false)
//                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
//
//                        finish()
//                    }
//                    .setNegativeButton(getString(R.string.No)) { dialog, _ -> dialog.cancel() }
//                val alertDialog = alertDialogBuilder.create()
//                alertDialog.show()
//            }
//
//        }


//        val layoutManager = LinearLayoutManager(this)
//        binding.rvStory.layoutManager = layoutManager
//        binding.rvStory.setHasFixedSize(false)

        listStoryPagingAdapter = ListStoryPagingAdapter()
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.rvStory.adapter = listStoryPagingAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                listStoryPagingAdapter.retry()
            }
        )


        binding.btnAddStory.setOnClickListener{
            startActivity(Intent(this@MainActivity, AddStoryActivity::class.java))
        }
    }

//    private fun setStoryItem(story: List<ListStory>?) {
//        val adapter = story?.let { StoryAdapter(it) }
//        adapter?.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
//            override fun onItemClicked(listStory: ListStory) {
//                val i = Intent(this@MainActivity, DetailActivity::class.java)
//                i.putExtra(DetailActivity.EXTRA_STORY, listStory)
//                startActivity(i)
//            }
//        })
//        binding.rvStory.adapter = adapter
//    }



    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(userPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                AddStoryActivity.TOKEN = user.token
                listPagingViewModel = ViewModelProvider(
                    this,
                    ViewModelFactoryPaging(user.token)
                )[ListPagingViewModel::class.java]
                listPagingViewModel.list.observe(this, {
                    listStoryPagingAdapter.submitData(lifecycle, it)
                })
                listStoryPagingAdapter.refresh()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_logout -> logoutDialog()
            R.id.ic_map -> startActivity(Intent(this@MainActivity, MapsActivity::class.java))
        }
        return true
    }


    private fun logoutDialog() {
        val dialogMessage = getString(R.string.logout_msg)
        val dialogTitle = getString(R.string.logout)


        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)

        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                mainViewModel.logout()
                finish()
            }
            .setNegativeButton(getString(R.string.No)) { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}