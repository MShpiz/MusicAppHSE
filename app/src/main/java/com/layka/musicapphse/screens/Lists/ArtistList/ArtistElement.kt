package com.layka.musicapphse.screens.Lists.ArtistList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R


@Composable
fun ArtistElement(data: ArtistData, onClick: () -> Unit) {
    Row(
        Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClick() }) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(null) // в апи нет каринок исполнителей
                .networkCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = data.name,
            placeholder = painterResource(R.drawable.profile_picture), // TODO("заменить дефолтную картинку")
            error = painterResource(id = R.drawable.profile_picture),
            modifier = Modifier
                //.padding(3.dp)
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
                .width(70.dp)
                .height(70.dp)
                .clip(CircleShape)
        )
        Text(
            text = data.name,
            fontSize = 23.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }

}