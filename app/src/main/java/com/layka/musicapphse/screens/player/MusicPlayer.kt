package com.layka.musicapphse.screens.player

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.seconds

private enum class Anchors {
    Collapsed,
    Expanded
}

@Composable
fun MusicPlayer(playerModel: PlayerModel = hiltViewModel(), navController: NavController) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeight = configuration.screenHeightDp.dp
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher
    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            playerModel.queueModel.onDestroy()
        }
    }
    lifecycleOwner.lifecycle.addObserver(observer)
    DisposableEffect(Unit) {
        onDispose {
            playerModel.queueModel.onDestroy()
        }
    }
    val getHeaders = remember {
        mutableStateOf(false)
    }
    if (!getHeaders.value) {
        getHeaders.value = true
        playerModel.getHeaders()
    }
    val dragState = remember {
        AnchoredDraggableState(
            initialValue = Anchors.Collapsed,
            anchors = DraggableAnchors {
                Anchors.Expanded at 0f
                Anchors.Collapsed at with(density) { (screenHeight - 140.dp).toPx() }.toFloat()
            },
            positionalThreshold = { d -> d * 0.3f },
            velocityThreshold = { Float.POSITIVE_INFINITY },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimationSpec
        )
    }

    val expandPlayer = fun() {
        coroutineScope.launch {
            dragState.animateTo(Anchors.Expanded)
        }
    }

    val collapsePlayer = fun() {
        coroutineScope.launch {
            dragState.animateTo(Anchors.Collapsed)
        }
    }
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (dragState.currentValue == Anchors.Expanded)
                    collapsePlayer()
                else
                    navController.popBackStack()
            }
        }
    }
    backDispatcher.addCallback(lifecycleOwner, backCallback)

    val playerPosition = remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            playerPosition.value = playerModel.queueModel.getCurrPosition().toFloat()
            delay(1.seconds / 30)
        }
    }


    if (
        !navController.currentBackStackEntryAsState()
            .value?.destination?.route.toString().startsWith("auth")
    ) {

        Box(
            Modifier
                .offset {
                    IntOffset(
                        x = 0,
                        y = dragState
                            .requireOffset()
                            .roundToInt(),
                    )
                }
                .anchoredDraggable(dragState, Orientation.Vertical)

        ) {
            AnimatedVisibility(
                visible = (dragState.targetValue == Anchors.Collapsed),
                enter = fadeIn(),
                exit = fadeOut(),
                // modifier=Modifier
            )
            {
                CollapsedState(
                    playerPosition = playerPosition,
                    onClicked = { expandPlayer() }
                )
            }


            AnimatedVisibility(
                visible = (dragState.targetValue != Anchors.Collapsed),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                PlayerExpanded(
                    playerPosition = playerPosition,
                    collapsePlayer = collapsePlayer,
                    playerModel = playerModel,
                    navController = navController
                )
            }
        }

    }
}
