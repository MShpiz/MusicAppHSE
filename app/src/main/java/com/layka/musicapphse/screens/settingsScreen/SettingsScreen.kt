package com.layka.musicapphse.screens.settingsScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    val newEmail = remember { mutableStateOf("") }
    val newUsername = remember { mutableStateOf("") }
    val showEmailForm = remember { mutableStateOf(false) }
    val showUsernameForm = remember { mutableStateOf(false) }
    val gotData = remember { mutableStateOf(false) }
    val offlineOn = remember { mutableStateOf(true) }
    viewModel.getCurrentMode(offlineOn)

    if (!gotData.value && !offlineOn.value) {
        gotData.value = true
        viewModel.getData(newEmail, newUsername)
    }

    val showToast = fun(text: String) {
        Toast.makeText(
            context, text, Toast.LENGTH_SHORT
        ).show()
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
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .padding(bottom=100.dp)
                .fillMaxSize()
        ) {
            item {
                Column(Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 10.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {

                        Text(
                            text = stringResource(id = R.string.turn_on_offline),
                            modifier = Modifier
                                .padding(vertical = 10.dp, horizontal = 5.dp)
                        )
                        Switch(
                            checked = offlineOn.value,
                            onCheckedChange = {
                                viewModel.changeCurrentMode(it)
                                offlineOn.value = it
                                if (offlineOn.value) {
                                    viewModel.getData(newEmail, newUsername)
                                }
                            }
                        )
                    }
                    if (!offlineOn.value) {
                        Button(
                            onClick = { showEmailForm.value = !showEmailForm.value },
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
                            onClick = { showUsernameForm.value = !showUsernameForm.value },
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
                                modifier = Modifier
                                    .padding(vertical = 2.dp, horizontal = 1.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxWidth(0.75f)
                            )
                        }

                        if (showEmailForm.value || showUsernameForm.value) {
                            Button(
                                onClick = {
                                    viewModel.updateProfile(
                                        username = newUsername.value,
                                        email = newEmail.value,
                                        callBack = showToast
                                    )
                                    showEmailForm.value = false
                                    showUsernameForm.value = false
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 10.dp)
                                    .fillMaxWidth(0.75f)
                            ) {
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
        }
    }
}