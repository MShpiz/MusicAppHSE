package com.layka.musicapphse.screens.Lists.AlbumList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastJoinToString
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R

@Composable
fun AlbumSquareElement(
    name: String,
    artistName: List<String>,
    cover: ImageRequest? = null
    //navController: NavController,
) {
    Column (
        Modifier
            .clickable { TODO() }
            .padding(start = 30.dp, bottom = 10.dp)
            .height(150.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("127.0.0.1:8000")
                .networkCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = "$name Album by $artistName",
            placeholder = painterResource(R.drawable.ic_launcher_background), // TODO("заменить дефолтную картинку")
            error = painterResource(id = R.drawable.ic_launcher_background),
            modifier = Modifier
                .padding(3.dp)
                .padding(end = 5.dp)
                .align(Alignment.Start)
                .height(100.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(
            text=name,
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = artistName.fastJoinToString { it } ,
            style = TextStyle(color = Color.Gray),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 13.sp,
            modifier = Modifier.align(Alignment.Start)
        )
    }

}