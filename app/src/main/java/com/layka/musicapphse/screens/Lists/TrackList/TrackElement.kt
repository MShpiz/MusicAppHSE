package com.layka.musicapphse.screens.Lists.TrackList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R

@Composable
fun TrackElement(
    id: Int,
    name: String,
    artistName: List<String>,
    artistId: List<Int>,
    duration: String,
    cover: String? = null,
    index: Int,
    showCover: Boolean,
    showArtistName: Boolean,
) {
    Row(modifier = Modifier
        .padding(top = 5.dp)
        .fillMaxWidth()
        .height(50.dp)
        .clickable {  }
    )
    {
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
                    .data("127.0.0.1:8000")
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = name,
                placeholder = painterResource(R.drawable.ic_launcher_background), // TODO("заменить дефолтную картинку")
                error = painterResource(id = R.drawable.ic_launcher_background),
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
                    text = artistName.joinToString { it },
                    style = TextStyle(color = Color.Gray),
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
        Text(
            text = duration,
            style = TextStyle(color = Color.Gray),
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(end = 5.dp)
                .width(55.dp)

        )
        // TODO ("иконка меню")
    }
}

@Preview()
@Composable
fun TrackElementPreview() {
    Column {
        TrackElement(
            1,
            "AAAA",
            listOf("Artist 1", "Artist 2", "Artist 3"),
            listOf(1, 2, 3),
            "11:32",
            index = 1,
            showCover = true,
            showArtistName = true
        )
        TrackElement(
            2,
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            listOf("Artist 1"),
            listOf(1),
            "99:11:32",
            index = 2,
            showCover = false,
            showArtistName = true
        )
        TrackElement(
            3,
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            listOf("Artist 1"),
            listOf(1),
            "11:32",
            index = 3,
            showCover = true,
            showArtistName = false
        )
    }
}