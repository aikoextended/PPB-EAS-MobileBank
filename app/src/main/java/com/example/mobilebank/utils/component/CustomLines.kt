package com.example.mobilebank.utils.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun GrayLine(modifier: Modifier = Modifier, color: Color, strokeLine: Float) {
    Canvas(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            cap = StrokeCap.Butt,
            strokeWidth = strokeLine
        )
    }
}