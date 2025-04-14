package com.layka.musicapphse.screens.Lists.PlaylistList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.layka.musicapphse.R

@Composable
fun PlaylistItem(
    shortPlaylistData: ShortPlaylistData,
    onClick: () -> Unit,
    showCheckBox: Boolean = false,
    onCheckBoxChecked: (id: Int, check: Boolean) -> Unit = { id, check -> }
) {
    val checked = remember { mutableStateOf(false) }
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() })
    {
        if (showCheckBox) {
            Checkbox(
                checked = checked.value,
                onCheckedChange = {
                    checked.value = it; onCheckBoxChecked(
                    shortPlaylistData.id,
                    it
                )
                },
                Modifier.padding(end = 10.dp, start = 5.dp)
            )
        }
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.music_note),
            contentDescription = "playlist",
            Modifier
                .padding(3.dp)
                .padding(end = 5.dp)
                .align(Alignment.CenterVertically)
                .width(50.dp)
                .height(50.dp)
        )

        Column(
            Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 10.dp)
                .weight(2f)
        ) {
            Text(
                text = shortPlaylistData.name,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = shortPlaylistData.description,
                style = TextStyle(color = Color.Gray),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 13.sp
            )
        }
    }
}