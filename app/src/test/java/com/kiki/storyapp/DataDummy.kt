package com.kiki.storyapp

import com.kiki.storyapp.Response.ListStory
import com.kiki.storyapp.Response.StoryResponse


object DataDummy {

    fun generateDummyStoriesResponse(): StoryResponse {
        return StoryResponse(generateDummyStoryListResponse(),
            false,
            "Success")
    }




    fun generateDummyStoryListResponse(): List<ListStory> {
        val items: MutableList<ListStory> = arrayListOf()
        for (i in 0..100) {
            val story = ListStory(
                i.toString(),
                "$i $i $i",
                "author $i ",
                "desc $i",
                null,
                "Story- $i",
                null
            )
            items.add(story)
        }
        return items
    }

    fun generateDummyListStory(): List<ListStory> {
        val stories = arrayListOf<ListStory>()

        for (i in 0 until 10) {
            val story = ListStory(
                "https://story-api.dicoding.dev/images/stories/photos-1683905264786_4DWhg1b8.jpg",
                "2023-05-12T15:27:44.788Z",
                "kiki",
                "ini foto dari kiki",
                null,
                "story-LKdLLasTDbzwGDjA",
                null
            )

            stories.add(story)
        }
        return stories

    }

}