package com.kiki.storyapp.ListPaging

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kiki.storyapp.Detail.DetailActivity
import com.kiki.storyapp.Response.ListStory
import com.kiki.storyapp.databinding.ItemStoryBinding

class ListStoryPagingAdapter : PagingDataAdapter<ListStory, ListStoryPagingAdapter.ViewHolder>(
    DIFF_CALLBACK
) {
    class ViewHolder(var Binding:ItemStoryBinding) : RecyclerView.ViewHolder(Binding.root){
        fun bind(dataStoriesst: ListStory){
            Binding.tvNameUser.text = dataStoriesst.name
            Glide.with(itemView.context)
                .load(dataStoriesst.photoUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(Binding.ivItemPhoto)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context,DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_URL, dataStoriesst.photoUrl)
                intent.putExtra(DetailActivity.EXTRA_NAME, dataStoriesst.name)
                intent.putExtra(DetailActivity.EXTRA_DESC, dataStoriesst.description)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val Binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(Binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userStoryStories = getItem(position)
        if (userStoryStories != null){
            holder.bind(userStoryStories)
        }
    }

    companion object{
        val DIFF_CALLBACK = object  : DiffUtil.ItemCallback<ListStory>(){
            override fun areItemsTheSame(
                oldItem: ListStory,
                newItem: ListStory
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStory,
                newItem: ListStory
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}