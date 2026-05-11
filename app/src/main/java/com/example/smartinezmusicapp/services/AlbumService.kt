package com.example.smartinezmusicapp.services

import com.example.smartinezmusicapp.models.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {

    @GET("api/albums")
    suspend fun getAllAlbums(): List<Album>

    @GET("api/albums/{id}")
    suspend fun getAlbumById(@Path("id") id: String): Album
}
