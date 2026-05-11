package com.example.smartinezmusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.smartinezmusicapp.navigation.Detail
import com.example.smartinezmusicapp.navigation.Home
import com.example.smartinezmusicapp.screens.DetailScreen
import com.example.smartinezmusicapp.screens.HomeScreen
import com.example.smartinezmusicapp.ui.theme.SMartinezMusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SMartinezMusicAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Home
                    ) {
                        composable<Home> {
                            HomeScreen(
                                innerPadding = innerPadding,
                                navController = navController
                            )
                        }
                        composable<Detail> { backStack ->
                            val detail = backStack.toRoute<Detail>()
                            DetailScreen(
                                albumId = detail.id,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    SMartinezMusicAppTheme {
        HomeScreen()
    }
}
