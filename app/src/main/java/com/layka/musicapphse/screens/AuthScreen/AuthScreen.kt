package com.layka.musicapphse.screens.AuthScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.layka.musicapphse.R
import com.layka.musicapphse.services.AuthResult

@Composable
fun AuthScreen(navController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()) {
    val needRegister = remember {
        mutableStateOf(false)
    }
    val registerEmail = remember {
        mutableStateOf("")
    }
    val registerPassword = remember {
        mutableStateOf("")
    }
    val registerUsername = remember {
        mutableStateOf("")
    }
    val loginEmail = remember {
        mutableStateOf("")
    }
    val loginPassword = remember {
        mutableStateOf("")
    }


    val context = LocalContext.current // контекст для тоста

    val showToast = fun(text: String) {
        Toast.makeText(
            context, text, Toast.LENGTH_SHORT
        ).show()
    }
    if (authViewModel.authResult.value == AuthResult.OK) {
        navController.navigate("main_screen")
    }



    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.Center)
        ) {
            if (!needRegister.value) {
                TextField(
                    value = loginEmail.value,
                    onValueChange = { value -> loginEmail.value = value },
                    label = { androidx.compose.material3.Text(stringResource(id = R.string.email)) },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = loginPassword.value,
                    onValueChange = { value -> loginPassword.value = value },
                    label = { androidx.compose.material3.Text(stringResource(id = R.string.password)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {
                    authViewModel.login(
                        loginEmail.value,
                        loginPassword.value,
                        showToast
                    )
                }, Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.login))
                }
                TextButton(onClick = { needRegister.value = true }, Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.register),
                        style = TextStyle(color = colorResource(id = R.color.purple_200))

                    )
                }
            } else {
                TextField(
                    value = registerEmail.value,
                    onValueChange = { value -> registerEmail.value = value },
                    label = { Text(stringResource(id = R.string.email)) },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = registerPassword.value,
                    onValueChange = { value -> registerPassword.value = value },
                    label = { Text(stringResource(id = R.string.password)) },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = registerUsername.value,
                    onValueChange = { value -> registerUsername.value = value },
                    label = { androidx.compose.material3.Text(stringResource(id = R.string.username)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        authViewModel.register(
                            registerEmail.value,
                            registerPassword.value,
                            registerUsername.value,
                            showToast
                        )
                    },
                    Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.register))
                }
                TextButton(onClick = { needRegister.value = false }, Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = TextStyle(color = colorResource(id = R.color.purple_200))
                    )
                }
            }
        }
    }
}

//@Composable
//@Preview
//fun AuthScreenPreview() {
//    AuthScreen()
//}
