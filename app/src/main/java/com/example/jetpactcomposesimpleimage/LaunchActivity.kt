package com.example.jetpactcomposesimpleimage

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpactcomposesimpleimage.ComposeActivity.Companion.ALIGNMENT_KEY
import com.example.jetpactcomposesimpleimage.ComposeActivity.Companion.IMAGE_KEY
import com.example.jetpactcomposesimpleimage.ComposeActivity.Companion.SCALE_KEY

class LaunchActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_STATE_KEY = "image"
        const val ALIGNMENT_STATE_KEY = "alignment"
        const val SCALE_STATE_KEY = "scale"
    }

    private val selectedImage = mutableStateOf(ImagesTypeEnum.BIGGER)
    private val selectedAlignment = mutableStateOf(AligmentEnum.CENTER)
    private val selectedScale = mutableStateOf(ContentScaleEnum.NONE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            selectedImage.value = savedInstanceState.getSerializable(IMAGE_STATE_KEY) as ImagesTypeEnum
            selectedAlignment.value = savedInstanceState.getSerializable(ALIGNMENT_STATE_KEY) as AligmentEnum
            selectedScale.value = savedInstanceState.getSerializable(SCALE_STATE_KEY) as ContentScaleEnum
        }

        setContent {
            Column (modifier = Modifier.fillMaxWidth()){
                Row {
                    Column(
                        modifier = Modifier.weight(1f).padding(4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text("Image : ", fontSize = 16.sp)
                        Text("Alignment : ", fontSize = 16.sp)
                        Text("Scale : ", fontSize = 16.sp)
                    }
                    Column(
                        modifier = Modifier.weight(1f).padding(4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        DropdownDemo(
                            enumValues<ImagesTypeEnum>().toList(), selectedImage
                        )
                        DropdownDemo(
                            enumValues<AligmentEnum>().toList(), selectedAlignment
                        )
                        DropdownDemo(
                            enumValues<ContentScaleEnum>().toList(), selectedScale
                        )
                    }
                }

                Button(
                    onClick = { startActivity(
                        Intent(this@LaunchActivity, ComposeActivity::class.java).apply {
                            putExtra(IMAGE_KEY, selectedImage.value)
                            putExtra(ALIGNMENT_KEY, selectedAlignment.value)
                            putExtra(SCALE_KEY, selectedScale.value)
                        }
                    )},
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
                ) {
                    Text("View Image")
                }
            }
        }
    }

    @Composable
    fun <T> DropdownDemo(items: List<T>, selected: MutableState<T>) {
        var showMenu by remember { mutableStateOf(false) }

        DropdownMenu(
            toggle = {
                Text(
                    text = selected.value.toString(),
                    modifier = Modifier.clickable(onClick = { showMenu = true }),
                    fontSize = 16.sp
                )
            },
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            toggleModifier = Modifier.background(Color.LightGray)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    onClick = {
                        selected.value = items[index]
                        showMenu = false
                    }
                ) {
                    Text(text = s.toString())
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(IMAGE_STATE_KEY, selectedImage.value)
        outState.putSerializable(ALIGNMENT_STATE_KEY, selectedAlignment.value)
        outState.putSerializable(SCALE_STATE_KEY, selectedScale.value)
    }
}

enum class ImagesTypeEnum(val descriptor: String, @DrawableRes val imageId: Int) {
    BIGGER("Bigger Image - Fall", R.drawable.fall),
    SMALLER("Smaller Image - Lion", R.drawable.lion),
    TALLER("Taller Image - Tree", R.drawable.tree),
    LONGER("Longer Image - Bridge", R.drawable.bridge),
    JUMP("Tall Image - Jump", R.drawable.jump),
    ATHLETIC("Long Image - Track", R.drawable.athletic);

    override fun toString(): String {
        return descriptor
    }

    companion object {
        fun getEnum(value: String): ImagesTypeEnum {
            return values().first { it.descriptor == value }
        }
    }
}

enum class AligmentEnum(val descriptor: String, val alignment: Alignment) {
    TOP_START("Top Start", Alignment.TopStart),
    TOP_CENTER("Top Center", Alignment.TopCenter),
    TOP_END("Top End", Alignment.TopEnd),
    CENTER_START("Center Start", Alignment.CenterStart),
    CENTER("Center", Alignment.Center),
    CENTER_END("Center End", Alignment.CenterEnd),
    BOTTOM_START("Bottom Start", Alignment.BottomStart),
    BOTTOM_CENTER("Bottom Center", Alignment.BottomCenter),
    BOTTOM_END("Bottom End", Alignment.BottomEnd);

    override fun toString(): String {
        return descriptor
    }

    companion object {
        fun getEnum(value: String): AligmentEnum {
            return values().first { it.descriptor == value }
        }
    }
}

enum class ContentScaleEnum(val descriptor: String, val scaleType: ContentScale) {
    NONE("None", ContentScale.None),
    INSIDE("Inside", ContentScale.Inside),
    CROP("Crop", ContentScale.Crop),
    FIT("Fit", ContentScale.Fit),
    FILL_BOUNDS("Fill Bounds", ContentScale.FillBounds),
    FILL_HEIGHT("Fill Height", ContentScale.FillHeight),
    FILL_WIDTH("Fill Width", ContentScale.FillWidth);

    override fun toString(): String {
        return descriptor
    }

    companion object {
        fun getEnum(value: String): ContentScaleEnum {
            return values().first { it.descriptor == value }
        }
    }
}