package dev.rivu.golpoai.data.models

data class SavedStory(
    val id: String,
    val story: Story,
)

data class StoryMetadata (
    val title: String,
    val prompt: String,
    val genre: String,
    val language: String,
    val isOffline: Boolean,
    val createdAt: Long,
)

data class Story(
    val storyMetadata: StoryMetadata,
    val storyText: String
)
