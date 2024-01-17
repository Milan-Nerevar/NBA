package com.nerevar.nba.core.compose

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Checks if the LazyColumn is scrolled to the end based on the given threshold.
 *
 * @param threshold The threshold value to determine the end of the list.
 *                  A positive value indicates how many items from the end to trigger the condition.
 *                  For example, a threshold of 5 will trigger the condition when 5 items are left to scroll.
 * @return true if the end of the list is reached (within the threshold), false otherwise.
 */
@Composable
private fun LazyListState.isScrolledToTheEnd(threshold: Int): Boolean {
    // Get the index of the last visible item in the LazyColumn.
    val lastVisibleIndex by remember { derivedStateOf { layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 } }

    // Calculate the index threshold, which indicates when the condition for reaching the end is met.
    val thresholdIndex by remember { derivedStateOf { layoutInfo.totalItemsCount - threshold - 1 } }

    // Check if the threshold index is within the range of visible items.
    val isScrolledToTheEnd by remember { derivedStateOf { thresholdIndex in 1..lastVisibleIndex } }

    return isScrolledToTheEnd
}

/**
 * A custom Composable function that creates a paginated LazyColumn with a threshold for triggering
 * a refresh when scrolling to the end of the list.
 *
 * @param modifier The modifier to be applied to the LazyColumn.
 * @param threshold The threshold value to trigger a refresh when reaching the end of the list.
 * @param state The LazyListState to control and observe the LazyColumn's scroll state.
 * @param contentPadding The padding to be applied to the content within the LazyColumn.
 * @param reverseLayout When true, the items are laid out from bottom to top; otherwise, from top to bottom.
 * @param verticalArrangement The vertical arrangement strategy for items in the LazyColumn.
 * @param horizontalAlignment The horizontal alignment strategy for items in the LazyColumn.
 * @param flingBehavior The FlingBehavior for scrolling in the LazyColumn.
 * @param userScrollEnabled When true, enables user-initiated scrolling; otherwise, scrolling is disabled.
 * @param onRefresh The callback to be invoked when the end of the list is reached based on the threshold.
 * @param content The composable lambda block to populate the LazyColumn with items.
 */
@Composable
@Suppress("LongParameterList")
fun LazyColumnPaginated(
    modifier: Modifier = Modifier,
    threshold: Int = 5,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    onRefresh: () -> Unit,
    content: LazyListScope.() -> Unit,
) {
    val isScrolledToTheEnd = state.isScrolledToTheEnd(threshold)
    LaunchedEffect(key1 = isScrolledToTheEnd) {
        if (isScrolledToTheEnd) {
            onRefresh()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
    ) {
        content()
    }
}
