package dev.rivu.golpoai.data.models

data class SavedStory(
    val id: String,
    val prompt: String,
    val genre: String,
    val story: String,
    val createdAt: Long
)
