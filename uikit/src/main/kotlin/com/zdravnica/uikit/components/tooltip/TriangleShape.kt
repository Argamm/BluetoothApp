package com.zdravnica.uikit.components.tooltip

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.zdravnica.uikit.COUNT_TWO

class TriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        path.apply {
            moveTo(x = size.width / COUNT_TWO, y = size.height)
            lineTo(x = size.width, y = 0f)
            lineTo(x = 0f, y = 0f)
            close()
        }
        return Outline.Generic(path = path)
    }
}