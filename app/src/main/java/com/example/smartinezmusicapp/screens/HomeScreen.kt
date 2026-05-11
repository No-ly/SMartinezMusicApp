package com.example.smartinezmusicapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartinezmusicapp.navigation.Detail
import com.example.smartinezmusicapp.components.AlbumCard
import com.example.smartinezmusicapp.components.MiniPlayer
import com.example.smartinezmusicapp.components.RecentlyPlayedItem
import com.example.smartinezmusicapp.models.Album
import com.example.smartinezmusicapp.services.AlbumService
import com.example.smartinezmusicapp.ui.theme.LightBackground
import com.example.smartinezmusicapp.ui.theme.PurpleGradientEnd
import com.example.smartinezmusicapp.ui.theme.PurpleGradientStart
import com.example.smartinezmusicapp.ui.theme.PurplePrimary
import com.example.smartinezmusicapp.ui.theme.TextMedium
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController = rememberNavController()
) {
    val BASE_URL = "https://musicapi.pjasoft.com/"
    var albums by remember { mutableStateOf(listOf<Album>()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentAlbum by remember { mutableStateOf<Album?>(null) }

    LaunchedEffect(key1 = true) {
        try {
            Log.i("HomeScreen", "Loading albums from API")
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val result = async(Dispatchers.IO) {
                val albumService = retrofit.create(AlbumService::class.java)
                albumService.getAllAlbums()
            }
            albums = result.await()
            if (albums.isNotEmpty()) {
                currentAlbum = albums[0]
            }
            isLoading = false
            Log.i("HomeScreen", "Loaded ${albums.size} albums")
        } catch (e: Exception) {
            Log.e("HomeScreen", e.message.toString())
            errorMessage = e.message
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(innerPadding)
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PurplePrimary)
            }
        } else if (errorMessage != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            // Scrollable content with mini player at bottom
            Column(
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // ─── HEADER WITH GRADIENT ───
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            PurpleGradientStart,
                                            PurpleGradientEnd,
                                            LightBackground
                                        )
                                    )
                                )
                                .padding(top = 16.dp, bottom = 24.dp)
                        ) {
                            Column {
                                // Top bar with menu and search
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = "Menu",
                                            tint = Color.White,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                    IconButton(onClick = { }) {
                                        Icon(
                                            imageVector = Icons.Filled.Search,
                                            contentDescription = "Search",
                                            tint = Color.White,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }

                                // Greeting
                                Column(
                                    modifier = Modifier.padding(horizontal = 24.dp)
                                ) {
                                    Text(
                                        text = "Good Morning!",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                    Text(
                                        text = "Santiago Martinez",
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }

                    // ─── ALBUMS SECTION ───
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Albums",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "See more",
                                style = MaterialTheme.typography.bodyMedium,
                                color = PurplePrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // LazyRow for albums carousel
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(albums) { album ->
                                AlbumCard(
                                    album = album,
                                    onClick = {
                                        navController.navigate(Detail(id = album.id))
                                    }
                                )
                            }
                        }
                    }

                    // ─── RECENTLY PLAYED SECTION ───
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Recently Played",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "See more",
                                style = MaterialTheme.typography.bodyMedium,
                                color = PurplePrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Recently played items
                    items(albums) { album ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            RecentlyPlayedItem(
                                album = album,
                                onClick = {
                                    navController.navigate(Detail(id = album.id))
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Bottom spacing for mini player
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // ─── MINI PLAYER ───
            MiniPlayer(
                album = currentAlbum,
                isPlaying = isPlaying,
                onPlayPauseClick = { isPlaying = !isPlaying }
            )
        }
    }
}
