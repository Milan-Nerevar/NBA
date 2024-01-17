package com.nerevar.nba.core.compose

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext

@Immutable
sealed interface StringResource {

    /**
     * A string resource represented by [string].
     */
    @Immutable
    @JvmInline
    value class ByString(val string: String) : StringResource

    /**
     * A string resource represented by string resource [res] and optional [args].
     */
    @Immutable
    data class ByResource(@StringRes val res: Int, val args: List<Any>) : StringResource
}

@Stable
fun stringResource(string: String) = StringResource.ByString(string)

@Stable
fun stringResource(
    @StringRes res: Int,
    vararg arg: Any
) = StringResource.ByResource(res, arg.toList())

/**
 * Convert [StringResource] into a String.
 */
val StringResource.value: String
    @Stable @Composable get() = getValue(LocalContext.current)

/**
 * Convert [StringResource] into a String.
 *
 * @param context to use to access [android.content.res.Resources]
 */
fun StringResource.getValue(context: Context): String = when (this) {
    is StringResource.ByResource -> context.getString(res, *args.toTypedArray())
    is StringResource.ByString -> this.string
}


