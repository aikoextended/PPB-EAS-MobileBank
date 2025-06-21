package com.example.mobilebank.utils.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilebank.R

@Composable
fun CopyButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick,
        modifier
            .size(40.dp)
            .padding(end = 10.dp, top = 10.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_copy),
            contentDescription = "Copy",
            modifier.size(30.dp)
        )
    }
}

@Composable
fun EyeIconButton(onClick: () -> Unit, modifier: Modifier = Modifier, isVisible: Boolean) {
    IconButton(
        onClick = onClick,
        modifier
            .clip(CircleShape)
            .background(color = Color(0XFFECEDF1))
    ) {
        Icon(
            painter = painterResource(if (isVisible) R.drawable.icon_hide else R.drawable.icon_unhide),
            contentDescription = "Eye",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun CommonButton(onClick: () -> Unit, modifier: Modifier = Modifier, label: String) {
    TextButton(
        onClick = onClick,
        modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color(0XFFECEDF1))
    ) {
        Text(
            text = label, style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212821)
            )
        )
    }
}

@Composable
fun CircleButton(onClick: () -> Unit, modifier: Modifier = Modifier, iconImage: Int, sizeImage: Dp) {
    IconButton(
        onClick = onClick,
        modifier
            .clip(CircleShape)
            .background(color = Color(0XFFECEDF1))
    ) {
        Icon(
            painter = painterResource(iconImage),
            contentDescription = "Icon",
            modifier = Modifier.size(sizeImage)
        )
    }
}

@Preview
@Composable
private fun CopyButtonPreview() {
    CopyButton(
        modifier = Modifier,
        onClick = {}
    )
}

@Preview(showBackground = false)
@Composable
fun EyeIconButtonPreview() {
    EyeIconButton(onClick = {}, modifier = Modifier, isVisible = true)
}

@Preview(showBackground = false)
@Composable
fun CommonButtonPreview() {
    CommonButton(onClick = {}, modifier = Modifier, label = "Pindah Dana")
}


