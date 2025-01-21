package com.layka.musicapphse.customComposableElements.ScreenWithImageHeader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.layka.musicapphse.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithImageHeader(
    header: @Composable () -> Unit,
    body: @Composable () -> Unit,
    headerHeight: Int = 370,
    padding: PaddingValues
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val modalBottomSheetHeight = screenHeight - headerHeight.dp

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scrollState = rememberScrollState()

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = modalBottomSheetHeight,
            sheetSwipeEnabled = scrollState.value == 0, // TODO("fix scroll")
            sheetDragHandle = null,
            sheetContent = { Box(Modifier.height(screenHeight - padding.calculateTopPadding())){body() }},
            modifier = Modifier.padding(padding)
        ) { bottomSheetInnerPadding ->
            Box(
                modifier = Modifier
                    .padding(bottomSheetInnerPadding),
                contentAlignment = Alignment.Center
            ) {
                header()
            }
        }
}