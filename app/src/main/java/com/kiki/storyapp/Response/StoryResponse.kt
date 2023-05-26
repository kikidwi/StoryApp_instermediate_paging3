package com.kiki.storyapp.Response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class StoryResponse(
    @field: SerializedName("listStory")
    val listStory: List<ListStory>,

    @field: SerializedName("error")
    val error: Boolean,

    @field: SerializedName("message")
    val message: String
)
@Entity(tableName = "liststorylocalst")
data class ListStory(
    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: Double? = null
)