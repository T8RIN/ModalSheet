package com.t8rin.modalsheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * Modal sheet that behaves like bottom sheet and draws over system UI.
 * Should be used on with the content which is not dependent on the outer data. For dynamic content use [ModalSheet]
 * overload with a 'data' parameter.
 *
 * @param visible True if modal should be visible.
 * @param onVisibleChange Called when visibility changes.
 * @param cancelable When true, this modal sheet can be closed with swipe gesture, tap on scrim or tap on hardware back
 * button. Note: passing 'false' does not disable the interaction with the sheet. Only the resulting state after the
 * sheet settles.
 * @param shape The shape of the bottom sheet.
 * @param elevation The elevation of the bottom sheet.
 * @param containerColor The background color of the bottom sheet.
 * @param contentColor The preferred content color provided by the bottom sheet to its
 * children. Defaults to the matching content color for [containerColor], or if that is not
 * a color from the theme, this will keep the same content color set above the bottom sheet.
 * @param scrimColor The color of the scrim that is applied to the rest of the screen when the
 * bottom sheet is visible. If the color passed is [Color.Unspecified], then a scrim will no
 * longer be applied and the bottom sheet will not block interaction with the rest of the screen
 * when visible.
 * @param content The content of the bottom sheet.
 */
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun ModalSheet(
    visible: Boolean,
    onVisibleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    sheetModifier: Modifier = Modifier,
    cancelable: Boolean = true,
    skipHalfExpanded: Boolean = true,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    elevation: Dp = BottomSheetDefaults.Elevation,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipHalfExpanded = skipHalfExpanded,
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            // Intercept and disallow hide gesture / action
            if (it == ModalBottomSheetValue.Hidden && !cancelable) {
                return@rememberModalBottomSheetState false
            }

            onVisibleChange(it == ModalBottomSheetValue.Expanded)

            true
        },
    )

    LaunchedEffect(visible) {
        if (visible) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    if (!visible && sheetState.currentValue == sheetState.targetValue && !sheetState.isVisible) {
        return
    }

    ModalSheet(
        sheetState = sheetState,
        onDismiss = {
            if (cancelable) {
                onVisibleChange(false)
            }
        },
        sheetModifier = sheetModifier,
        modifier = modifier,
        shape = shape,
        elevation = elevation,
        containerColor = containerColor,
        contentColor = contentColor,
        scrimColor = scrimColor,
        content = content,
    )
}

/**
 * Modal sheet that behaves like bottom sheet and draws over system UI.
 * Takes [ModalSheetState] as parameter to fine-tune sheet behavior.
 *
 * Note: In this case [ModalSheet] is always added to the composition. See [ModalSheet] overload with visible parameter,
 * or data object to conditionally add / remove modal sheet to / from the composition.
 *
 * @param sheetState The state of the underlying Material bottom sheet.
 * @param onDismiss Called when user taps on the hardware back button.
 * @param shape The shape of the bottom sheet.
 * @param elevation The elevation of the bottom sheet.
 * @param containerColor The background color of the bottom sheet.
 * @param contentColor The preferred content color provided by the bottom sheet to its
 * children. Defaults to the matching content color for [containerColor], or if that is not
 * a color from the theme, this will keep the same content color set above the bottom sheet.
 * @param scrimColor The color of the scrim that is applied to the rest of the screen when the
 * bottom sheet is visible. If the color passed is [Color.Unspecified], then a scrim will no
 * longer be applied and the bottom sheet will not block interaction with the rest of the screen
 * when visible.
 * @param content The content of the bottom sheet.
 */
@ExperimentalMaterial3Api
@Composable
fun ModalSheet(
    modifier: Modifier = Modifier,
    sheetModifier: Modifier = Modifier,
    sheetState: ModalSheetState,
    onDismiss: (() -> Unit)?,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    elevation: Dp = BottomSheetDefaults.Elevation,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    content: @Composable ColumnScope.() -> Unit,
) {
    FullscreenPopup(
        onDismiss = onDismiss,
    ) {
        Box(Modifier.fillMaxSize()) {
            ModalSheetLayout(
                sheetModifier = sheetModifier,
                modifier = modifier.align(Alignment.BottomCenter),
                sheetState = sheetState,
                sheetShape = shape,
                sheetElevation = elevation,
                sheetContainerColor = containerColor,
                sheetContentColor = contentColor,
                scrimColor = scrimColor,
                sheetContent = content,
                content = {}
            )
        }
    }
}