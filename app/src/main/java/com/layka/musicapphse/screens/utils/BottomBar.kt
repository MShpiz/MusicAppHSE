package com.layka.musicapphse.screens.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.layka.musicapphse.screens.player.MusicPlayer

@Composable
fun BottomBar(navController: NavController) {
    Column {
        BottomAppBar(
            actions = {
                Row {
                    IconButton(
                        onClick = { navController.navigate("main_screen") },
                        Modifier.weight(1f)
                    ) {
                        Icon(Icons.Filled.Home, contentDescription = "main page")
                    }
                    IconButton(
                        onClick = { navController.navigate("search_screen") },
                        Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "search page",
                        )
                    }
                    IconButton(
                        onClick = { navController.navigate("profile_screen") },
                        Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "account",
                        )
                    }
                }
            }
        )

    }
}
