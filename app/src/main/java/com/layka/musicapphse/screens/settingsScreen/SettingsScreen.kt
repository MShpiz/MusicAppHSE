package com.layka.musicapphse.screens.settingsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import com.layka.musicapphse.R
import com.layka.musicapphse.screens.utils.BottomBar
import com.layka.musicapphse.screens.utils.TopBar


@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsScreenViewModel = hiltViewModel()
) {

    val newEmail = remember { mutableStateOf("") }
    val newUsername = remember { mutableStateOf("") }
    val showEmailForm = remember { mutableStateOf(false) }
    val showUsernameForm = remember { mutableStateOf(false) }
    val gotData = remember { mutableStateOf(false) }
    if (!gotData.value) {
        gotData.value = true
        viewModel.getData(newEmail, newUsername)
    }


    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                showScreenName = remember { mutableStateOf(true) },
                screenName = "Settings"
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Button(
                onClick = { showEmailForm.value = true },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.75f)
            ) {
                Text("change email")
            }
            if (showEmailForm.value) {
                TextField(
                    value = newEmail.value,
                    onValueChange = { newEmail.value = it },
                    label = { Text(stringResource(id = R.string.email)) },
                    maxLines = 10,
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 1.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.75f)
                )
            }

            Button(
                onClick = { showUsernameForm.value = true },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.75f)
            ) {
                Text("change username")
            }
            if (showUsernameForm.value) {
                TextField(
                    value = newUsername.value,
                    onValueChange = { newUsername.value = it },
                    label = { Text(stringResource(id = R.string.username)) },
                    maxLines = 10,
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 1.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.75f)
                )
            }

            if (showEmailForm.value || showUsernameForm.value) {
                Button(
                    onClick = {
                    viewModel.updateProfile(
                        username = newUsername.value,
                        email = newEmail.value
                    )
                },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(0.75f)) {
                    Text(text = "Update")
                }
            }
            Button(
                onClick = { viewModel.logout(navController) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.75f)
            ) {
                Text(text = "Log out")
            }
        }

    }
}