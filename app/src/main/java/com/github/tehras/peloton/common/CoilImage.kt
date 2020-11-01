package com.github.tehras.peloton.common

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.Px
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.Dp.Companion.Infinity
import androidx.compose.ui.unit.IntSize.Companion.Zero
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.request.ImageRequest
import coil.size.Scale
import coil.target.Target
import kotlinx.coroutines.*

@Composable
fun CoilImage(
    data: String,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    customize: ImageRequest.Builder .() -> Unit = {}
) = WithConstraints(modifier = modifier) {
    var width =
        if (constraints.maxWidth > Zero.width && constraints.maxWidth < Infinity.value.toInt()) {
            constraints.maxWidth
        } else {
            -1
        }

    var height =
        if (constraints.maxHeight > Zero.height && constraints.maxHeight < Infinity.value.toInt()) {
            constraints.maxHeight
        } else {
            -1
        }

    //if height xor width not able to be determined, make image a square of the determined dimension
    if (width == -1) width = height
    if (height == -1) height = width

    val context = ContextAmbient.current
    var animationJob: Job? = remember { null }
    val image = remember { mutableStateOf(ImageAsset(width, height)) }

    onCommit(data, width, height, context) {
        val target = object : Target {
            override fun onStart(placeholder: Drawable?) {
                placeholder?.apply {
                    animationJob?.cancel()
                    if (height != -1 && width != -1) {
                        animationJob = image.update(this, width, height)
                    } else if (height == -1) {
                        val scaledHeight = intrinsicHeight * (width / intrinsicWidth)
                        animationJob = image.update(this, width, scaledHeight)
                    } else if (width == -1) {
                        val scaledWidth = intrinsicWidth * (height / intrinsicHeight)
                        animationJob = image.update(this, scaledWidth, height)
                    }
                }
            }

            override fun onSuccess(result: Drawable) {
                animationJob?.cancel()
                animationJob = image.update(result)
            }

            override fun onError(error: Drawable?) {
                error?.run {
                    animationJob?.cancel()
                    animationJob = image.update(error)
                }
            }
        }
        val request = ImageRequest.Builder(context)
            .data(data)
            .size(width, height)
            .scale(Scale.FIT)
            .target(target)
            .listener(
                onError = { _, throwable ->
                    Log.e("ImageRequest", "Error loading $data", throwable)
                },
                onSuccess = { _, metadata ->
                    Log.e("ImageRequest", "Success loading $metadata")
                }
            )
            .apply { customize() }
            .build()

        val requestDisposable = Coil.imageLoader(context).enqueue(request)

        onDispose {
            image.value = ImageAsset(width = width, height = height)
            requestDisposable.dispose()
            animationJob?.cancel()
        }
    }

    Image(
        modifier = modifier,
        asset = image.value,
        colorFilter = colorFilter
    )
}

internal fun MutableState<ImageAsset>.update(
    drawable: Drawable,
    @Px width: Int? = null,
    @Px height: Int? = null
): Job? {
    if (drawable is Animatable) {
        (drawable as Animatable).start()

        return GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                val asset = drawable.toBitmap(
                    width = width ?: drawable.intrinsicWidth,
                    height = height ?: drawable.intrinsicHeight
                ).asImageAsset()

                withContext(Dispatchers.Main) { value = asset }
                delay(16)
            }
        }
    } else {
        value = drawable.toBitmap(
            width = width ?: drawable.intrinsicWidth,
            height = height ?: drawable.intrinsicHeight
        )
            .asImageAsset()
        return null
    }
}