package com.example.mobilebank.utils.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilebank.R


@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color,
    name: String,
    fontSize: Int
) {
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier.matchParentSize(), onDraw = {
            drawCircle(color = color)
        })
        Text(
            text = name,
            textAlign = TextAlign.Center,
            modifier = modifier,
            color = Color.White,
            fontSize = fontSize.sp
        )
    }
}

@Composable
fun LoyaltyCard(
    modifier: Modifier = Modifier,
    heightCard: Dp,
    widthCard: Dp,
    color: Color,
    textLoyalty: String,
    imageSize: Dp,
    fontSize: Int
) {
    Box(
        modifier = modifier
            .height(heightCard)
            .width(widthCard),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = modifier.matchParentSize(), onDraw = {
            drawRoundRect(color = color, cornerRadius = CornerRadius(100f, 100f))
        })
        Row(modifier = modifier.matchParentSize().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.icon_medal),
                modifier = modifier.size(imageSize),
                contentDescription = ""
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(text = textLoyalty, color = Color.Black, fontSize = fontSize.sp)
        }
    }
}