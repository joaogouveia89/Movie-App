package br.com.movieapp.moviePopularFeature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.com.movieapp.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    voteAverage: Double,
    imageUrl: String,
    id: Int,
    onClick: (id: Int) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        MovieRate(
            rate = voteAverage,
            modifier = modifier
                .align(Alignment.BottomStart)
                .zIndex(2f)
                .padding(start = 6.dp, bottom = 8.dp)
                .background(Color.Black)
        )

        Card(
            modifier = Modifier
                .fillMaxSize()
                .height(200.dp)
                .padding(4.dp)
                .clickable {
                    onClick(id)
                },
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .error(R.drawable.ic_error_image)
                        .placeholder(R.drawable.ic_placeholder)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.BottomCenter)
                        .background(Color.Black)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    MovieItem(
        modifier = Modifier,
        voteAverage = 7.2,
        imageUrl = "",
        id = 1,
        onClick = {}
    )
}