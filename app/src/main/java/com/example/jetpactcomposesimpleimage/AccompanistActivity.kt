package com.example.jetpactcomposesimpleimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.glide.GlideImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState
import dev.chrisbanes.accompanist.imageloading.MaterialLoadingImage
import dev.chrisbanes.accompanist.picasso.PicassoImage

class AccompanistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumn {
                item {
                    val itemModifier = Modifier.fillMaxWidth().height(200.dp).padding(4.dp)
                    Column {
                        Box(modifier = itemModifier) {
                            Image(
                                vectorResource(R.drawable.ic_launcher_background),
                                contentDescription = null,
                                modifier = Modifier.matchParentSize(),
                                contentScale = ContentScale.Crop

                            )
                            Image(
                                vectorResource(R.drawable.ic_launcher_foreground),
                                contentDescription = null,
                                modifier = Modifier.matchParentSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Image(
                            imageResource(R.drawable.header),
                            contentDescription = null,
                            modifier = itemModifier,
                            contentScale = ContentScale.Crop
                        )
                        CoilImage(
                            data = "https://picsum.photos/300/300",
                            modifier = itemModifier,
                            content = loadImage(),
                        )
                        PicassoImage(
                            data = "https://picsum.photos/300/300",
                            modifier = itemModifier,
                            content = loadImage()
                        )
                        GlideImage(
                            data = "https://picsum.photos/300/300",
                            modifier = itemModifier,
                            content = loadImage()
                        )
                    }
                }
            }
        }
    }

    private fun loadImage(): @Composable() (BoxScope.(imageLoadState: ImageLoadState) -> Unit) =
        { imageState ->
            when (imageState) {
                is ImageLoadState.Success -> {
                    MaterialLoadingImage(
                        result = imageState,
                        contentDescription = null,
                        fadeInEnabled = true,
                        fadeInDurationMs = 600,
                        contentScale = ContentScale.Crop,
                    )
                }
                is ImageLoadState.Error,
                is ImageLoadState.Empty -> {
                    Image(
                        imageResource(R.drawable.error300),
                        contentDescription = null
                    )
                }
                is ImageLoadState.Loading -> {
                    Box(Modifier.matchParentSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
            }
        }
}