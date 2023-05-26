package com.kiki.storyapp.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kiki.storyapp.Response.ListStory
import com.kiki.storyapp.databinding.ItemStoryBinding

class StoryAdapter(private val listStory: List<ListStory>) : RecyclerView.Adapter<StoryAdapter.ViewHolder>()  {
    private lateinit var onItemClickCallback: OnItemClickCallback


    class ViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listStory[position].photoUrl)
            .into(holder.binding.ivItemPhoto)

        holder.binding.tvNameUser.text = listStory[position].name
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listStory[position])
        }
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    interface OnItemClickCallback {
        fun onItemClicked(listStory: ListStory)
    }


}