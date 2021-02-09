package com.example.jetpactcomposesimpleimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.glide.GlideImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState
import dev.chrisbanes.accompanist.imageloading.MaterialLoadingImage
import dev.chrisbanes.accompanist.picasso.PicassoImage
import kotlin.math.roundToInt

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
                        Image(
                            ColorPainter(Color.Red),
                            contentDescription = null,
                            modifier = itemModifier,
                            contentScale = ContentScale.Crop
                        )
                        WaterMarkImage(
                            imageResource(R.drawable.header),
                            contentDescription = null,
                            modifier = itemModifier,
                            contentScale = ContentScale.Crop
                        )
                        CoilImage(
                            data = "https://dogtowndogtraining.com/wp-content/uploads/2012/06/300x300-061-e1340955308953.jpg",
                            modifier = itemModifier,
                            content = loadImage(),
                        )
                        PicassoImage(
                            data = "https://i.pinimg.com/originals/26/42/26/26422665b452967ebc301deadb2a036d.jpg",
                            modifier = itemModifier,
                            content = loadImage()
                        )
                        GlideImage(
                            data = "https://www.equathon.com/wp-content/uploads/2020/09/Jabiru-300x300-1.jpg",
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

@Composable
fun WaterMarkImage(
    bitmap: ImageBitmap,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    val waterMark = imageResource(R.drawable.watermark)
    val imagePainter = remember(bitmap) {
        DoubleImagePainter(bitmap, waterMark)
    }
    Image(
        painter = imagePainter,
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

data class DoubleImagePainter(
    private val firstImage: ImageBitmap,
    private val secondImage: ImageBitmap,
    private val srcOffset: IntOffset = IntOffset.Zero,
    private val firstSrcSize: IntSize = IntSize(firstImage.width, firstImage.height),
    private val secondSrcSize: IntSize = IntSize(secondImage.width, secondImage.height)
) : Painter() {

    private val firstSize: IntSize = validateSize(srcOffset, firstSrcSize)

    private var alpha: Float = 1.0f

    private var colorFilter: ColorFilter? = null

    override fun DrawScope.onDraw() {
        drawImage(
            firstImage,
            srcOffset,
            firstSrcSize,
            dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt()
            ),
            alpha = alpha,
            colorFilter = colorFilter
        )

        drawImage(
            secondImage,
            srcOffset,
            secondSrcSize,
            dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt()
            ),
            alpha = alpha,
            colorFilter = colorFilter
        )
    }

    /**
     * Return the dimension of the underlying [ImageBitmap] as it's intrinsic width and height
     */
    override val intrinsicSize: Size get() = firstSize.toSize()

    override fun applyAlpha(alpha: Float): Boolean {
        this.alpha = alpha
        return true
    }

    override fun applyColorFilter(colorFilter: ColorFilter?): Boolean {
        this.colorFilter = colorFilter
        return true
    }

    private fun validateSize(srcOffset: IntOffset, srcSize: IntSize): IntSize {
        require(
            srcOffset.x >= 0 &&
                    srcOffset.y >= 0 &&
                    srcSize.width >= 0 &&
                    srcSize.height >= 0 &&
                    srcSize.width <= firstImage.width &&
                    srcSize.height <= firstImage.height
        )
        return srcSize
    }
}
