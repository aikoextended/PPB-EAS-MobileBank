package com.example.mobilebank.utils.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private fun DrawScope.drawIndicator(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    radius: Dp,
    inactiveColor: Color,
    activeColor: Color,
) {
    val color = if (width > radius.toPx() * 2) activeColor else inactiveColor
    drawRoundRect(
        color = color,
        topLeft = Offset(x, y - height / 2),
        size = Size(width, height),
        cornerRadius = CornerRadius(radius.toPx(), radius.toPx())
    )
}

@Composable
fun CustomLineDotSlider(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    count: Int,
    activeLineWidth: Dp = 50.dp,
    circleSpacing: Dp = 2.dp,
    radius: Dp = 4.dp,
    inactiveColor: Color = Color(0xffF3F4F6),
    activeColor: Color = Color(0xff399918),
    totalWidth: Dp = 100.dp,
) {
    Canvas(modifier = modifier.width(width = totalWidth)) {
        val spacing = circleSpacing.toPx()
        val dotWidth = radius.toPx() * 2
        val dotHeight = radius.toPx() * 2

        val activeDotWidth = activeLineWidth.toPx() // toPx() is available here
        var x = 0f
        val y = center.y // center is available here

        repeat(count) { i ->
            val posOffset = pagerState.currentPageOffsetFraction + pagerState.currentPage
            val dotOffset = posOffset % 1
            val current = posOffset.toInt()

            val factor = (dotOffset * (activeDotWidth - dotWidth))

            val calculatedWidth = when {
                i == current -> activeDotWidth - factor
                i - 1 == current || (i == 0 && posOffset > count - 1) -> dotWidth + factor
                else -> dotWidth
            }

            drawIndicator(x, y, calculatedWidth, dotHeight, radius, inactiveColor, activeColor)
            x += calculatedWidth + spacing
        }
    }
}