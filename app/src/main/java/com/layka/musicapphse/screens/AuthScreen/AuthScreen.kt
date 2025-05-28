package com.layka.musicapphse.screens.AuthScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
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
    val showPassword = remember {
        mutableStateOf(false)
    }
    val isError = remember {
        mutableStateOf(false)
    }


    val context = LocalContext.current // контекст для тоста

    val showToast = fun(text: String) {
        Toast.makeText(
            context, text, Toast.LENGTH_SHORT
        ).show()
    }
    if (authViewModel.authResult.value == AuthResult.OK) {
        LaunchedEffect(Unit) {
            navController.navigate("main_screen") {
                popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                launchSingleTop = true
            }
        }
    }



    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth(0.75f)
                .align(Alignment.Center)
        ) {
            if (!needRegister.value) {
                TextField(
                    value = loginEmail.value,
                    onValueChange = { value -> loginEmail.value = value },
                    label = { androidx.compose.material3.Text(stringResource(id = R.string.email)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    maxLines = 1,
                    isError = isError.value,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )
                TextField(
                    value = loginPassword.value,
                    isError = isError.value,
                    onValueChange = { value -> loginPassword.value = value },
                    label = { androidx.compose.material3.Text(stringResource(id = R.string.password)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    maxLines = 1,
                    visualTransformation = if (!showPassword.value) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        IconButton(onClick = { showPassword.value = !showPassword.value }) {

                            Icon(
                                imageVector = if (!showPassword.value) ImageVector.vectorResource(id = R.drawable.visibility) else ImageVector.vectorResource(
                                    id = R.drawable.visibility_off
                                ),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Button(
                    onClick = {
                        authViewModel.login(
                            loginEmail.value,
                            loginPassword.value,
                            showToast,
                            isError
                        )
                    },
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
                TextButton(onClick = { needRegister.value = true; isError.value = false }, Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.register),
                        style = TextStyle(color = colorResource(id = R.color.purple_200))

                    )
                }
            } else {
                TextField(
                    value = registerEmail.value,
                    isError = isError.value,
                    onValueChange = { value -> registerEmail.value = value },
                    label = { androidx.compose.material3.Text(stringResource(id = R.string.email)) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
                TextField(
                    value = registerPassword.value,
                    isError = isError.value,
                    onValueChange = { value -> registerPassword.value = value },
                    label = { androidx.compose.material3.Text(stringResource(id = R.string.password)) },
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (!showPassword.value) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        IconButton(onClick = { showPassword.value = !showPassword.value }) {

                            Icon(
                                imageVector = if (!showPassword.value) ImageVector.vectorResource(id = R.drawable.visibility)
                                else ImageVector.vectorResource(
                                    id = R.drawable.visibility_off
                                ),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    }
                )
                TextField(
                    value = registerUsername.value,
                    isError = isError.value,
                    onValueChange = { value -> registerUsername.value = value },
                    maxLines = 1,
                    label = { androidx.compose.material3.Text(stringResource(id = R.string.username)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
                Button(
                    onClick = {
                        authViewModel.register(
                            registerEmail.value,
                            registerPassword.value,
                            registerUsername.value,
                            showToast,
                            isError
                        )
                    },
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(text = stringResource(id = R.string.register))
                }
                TextButton(onClick = { needRegister.value = false; ; isError.value = false}, Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = TextStyle(color = colorResource(id = R.color.purple_200))
                    )
                }
            }
        }
    }
}
//
//@Composable
//@Preview
//fun AuthScreenPreview() {
//    AuthScreen()
//}
