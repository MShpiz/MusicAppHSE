package com.layka.musicapphse.screens.player

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import kotlin.math.roundToInt

private enum class Anchors {
    Collapsed,
    Expanded
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicPlayer(trackData: MusicTrackData) {

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val heightCollapsed = remember { mutableStateOf(0.dp) } //in dp
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeight = configuration.screenHeightDp.dp
    val dragState = remember {
        AnchoredDraggableState(
            initialValue = Anchors.Collapsed,
            anchors = DraggableAnchors {
                Anchors.Expanded at 0f
                Anchors.Collapsed at with(density){(screenHeight - 100.dp).toPx()}.toFloat()
            },
            positionalThreshold = { d -> d * 0.3f },
            velocityThreshold = { Float.POSITIVE_INFINITY },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimationSpec
        )
    }

    var visible = remember { mutableStateOf(true) }

    Box(
        Modifier
            .fillMaxSize()
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
            exit = fadeOut()
        )
        {
            CollapsedState(trackData, Modifier.onSizeChanged {
                heightCollapsed.value = it.height.dp
                Log.v("THEHeight", "${heightCollapsed.value}")
                Log.v("THEHeight", "${with(density){(screenHeight - heightCollapsed.value).toPx()}} ${with(density){screenHeight.toPx()}}")
            })
        }


        AnimatedVisibility(
            visible = (dragState.targetValue != Anchors.Collapsed),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            PlayerExpanded(trackData = trackData)
        }
    }
}

@Preview
@Composable
fun PlayerPreview() {
    val trackData = MusicTrackData(1, "aaaa", listOf(Pair(1, "bbbb")), 50)
    MusicPlayer(trackData = trackData)
}