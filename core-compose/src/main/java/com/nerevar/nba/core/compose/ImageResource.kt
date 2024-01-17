package com.nerevar.nba.core.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.skydoves.landscapist.glide.GlideImage

sealed interface ImageResource {

    @Immutable
    @JvmInline
    value class ByUrl(val url: String) : ImageResource

    @Immutable
    @JvmInline
    value class ByDrawable(@DrawableRes val res: Int) : ImageResource

    @Immutable
    @JvmInline
    value class ByComposeColor(val color: Color) : ImageResource
}

@Stable
fun imageResource(url: String) = ImageResource.ByUrl(url)

@Stable
fun imageResource(@DrawableRes res: Int) = ImageResource.ByDrawable(res)

@Stable
fun imageResource(@DrawableRes color: Color) = ImageResource.ByComposeColor(color)

@Composable
fun ImageResource.Compose(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    shape: Shape = RectangleShape,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
) {
    when (this) {
        is ImageResource.ByDrawable -> Image(
            modifier = modifier.clip(shape),
            painter = painterResource(id = res),
            contentDescription = contentDescription,
            contentScale = contentScale,
            alignment = alignment,
        )

        is ImageResource.ByUrl -> GlideImage(
            imageModel = { url },
            modifier = modifier.clip(shape),
        )

        is ImageResource.ByComposeColor -> Box(modifier = modifier.background(color, shape))
    }
}