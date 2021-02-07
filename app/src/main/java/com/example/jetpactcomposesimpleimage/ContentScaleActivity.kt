package com.example.jetpactcomposesimpleimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ImagePainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource

class ContentScaleActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_KEY = "image"
        const val ALIGNMENT_KEY = "alignment"
        const val SCALE_KEY = "scale"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val image = intent.getSerializableExtra(IMAGE_KEY) as ImagesTypeEnum
        val alignment = intent.getSerializableExtra(ALIGNMENT_KEY) as AligmentEnum
        val scale = intent.getSerializableExtra(SCALE_KEY) as ContentScaleEnum
        setContent {
            LoadImage(image, alignment, scale)
        }
    }

    @Composable
    fun LoadImage(image: ImagesTypeEnum, alignment: AligmentEnum, scale: ContentScaleEnum) {
        val imageVector = rememberVectorPainter(vectorResource(R.drawable.ic_launcher_background))
        val imagePicture = ImagePainter(imageResource(image.imageId))
        Image(imagePicture,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alignment = alignment.alignment,
            contentScale = scale.scaleType
        )
    }
}
