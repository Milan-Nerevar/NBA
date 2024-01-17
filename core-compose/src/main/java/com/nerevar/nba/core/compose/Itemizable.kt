package com.nerevar.nba.core.compose

/**
 * @see [androidx.compose.foundation.lazy.items]
 */
interface Itemizable {

    /**
     * A factory of the content types for the item. The item compositions of the same type could
     * be reused more efficiently.
     */
    val contentType: Any
}
