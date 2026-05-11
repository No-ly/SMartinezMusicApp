package com.example.smartinezmusicapp.models

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val description: String,
    val image: String
)
