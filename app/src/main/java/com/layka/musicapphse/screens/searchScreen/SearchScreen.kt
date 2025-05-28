package com.layka.musicapphse.screens.searchScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.layka.musicapphse.R
import com.layka.musicapphse.screens.Lists.TrackList.TrackList
import com.layka.musicapphse.screens.utils.BottomBar
import com.layka.musicapphse.screens.utils.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()) {
    val expanded = rememberSaveable { mutableStateOf(false) }
    val textFieldState = rememberTextFieldState("")



    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                showScreenName = remember { mutableStateOf(true) },
                screenName = stringResource(id = R.string.search),
                showBackArrow = false
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->

        Column ( Modifier
            .padding(innerPadding)
            .padding(bottom=100.dp)) {


            Box(
                Modifier
                    .semantics { isTraversalGroup = true }) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.TopCenter)
                        .semantics { traversalIndex = 0f },
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = textFieldState.text.toString(),
                            onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                            onSearch = {
                                searchViewModel.getTracks(
                                    textFieldState.text.toString(),
                                    navController
                                )
                            },
                            expanded = expanded.value,
                            onExpandedChange = { expanded.value = it },
                            placeholder = { Text("Search") }
                        )
                    },
                    expanded = expanded.value,
                    onExpandedChange = { expanded.value = it },
                ) {
                    TrackList(
                        trackData = searchViewModel.foundTracks,
                        showCover = true,
                        showArtistName = true,
                        navController = navController
                    )
                }
            }
            TrackList(
                trackData = searchViewModel.foundTracks,
                showCover = true,
                showArtistName = true,
                navController = navController
            )
        }
    }
}