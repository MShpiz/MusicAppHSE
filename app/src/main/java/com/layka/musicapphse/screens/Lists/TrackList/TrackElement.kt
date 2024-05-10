package com.layka.musicapphse.screens.Lists.TrackList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R

@Composable
fun TrackElement(
    id: Int,
    name: String,
    artistName: String,
    duration: Int,
    cover: String?,
    headers: NetworkHeaders = NetworkHeaders.Builder().build(),
    index: Int,
    showCover: Boolean,
    showArtistName: Boolean,
    onClicked: () -> Unit,
    onAddToPlayList: () -> Unit,
    onDeleteTrack: (id: Int) -> Unit,
    onDownloadTrack: () -> Unit,
    showMenuBtn: Boolean = true,
    showCheckBox: Boolean = false,
    onChecked: (trackId: Int, checked: Boolean) -> Unit = { _, _ -> }
) {
    val menuExpanded = remember { mutableStateOf(false) }
    val checked = remember { mutableStateOf(false) }
    Row(modifier = Modifier
        .padding(top = 5.dp)
        .fillMaxWidth()
        .height(50.dp)
        .clickable { onClicked() }
    )
    {
        if (showCheckBox) {
            Checkbox(
                checked = checked.value,
                onCheckedChange = { checked.value = it; onChecked(id, checked.value) },
                Modifier.padding(end = 10.dp)
            )
        }
        Text(
            text = index.toString(),
            style = TextStyle(color = Color.Gray),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 5.dp, end = 6.dp)
                .width(20.dp)
        )

        if (showCover) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cover)
                    .httpHeaders(headers = headers)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = name,
                placeholder = painterResource(R.drawable.music_note),
                error = painterResource(id = R.drawable.music_note),
                modifier = Modifier
                    //.padding(3.dp)
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically)
                    .width(50.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }

        Column(
            Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 10.dp)
                .weight(2f)
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(bottom = 3.dp)
            )

            if (showArtistName) {
                Text(
                    text = artistName,
                    style = TextStyle(color = Color.Gray),
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
        Text(
            text = "${(duration / 60)}:${
                (duration % 60).toString().padStart(2, '0')
            }",
            style = TextStyle(color = Color.Gray),
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(end = 5.dp)
                .width(55.dp)

        )
        if (showMenuBtn) {
            IconButton(onClick = { menuExpanded.value = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "actions with track"
                )
                DropdownMenu(
                    expanded = menuExpanded.value,
                    onDismissRequest = { menuExpanded.value = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(stringResource(id = R.string.add_to_playlist))
                        },
                        onClick = { menuExpanded.value = false; onAddToPlayList() }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.delete_track)) },
                        onClick = { menuExpanded.value = false; onDeleteTrack(id) }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.downlodad_track)) },
                        onClick = { menuExpanded.value = false; onDownloadTrack() }
                    )
                }
            }
        }

    }
}
