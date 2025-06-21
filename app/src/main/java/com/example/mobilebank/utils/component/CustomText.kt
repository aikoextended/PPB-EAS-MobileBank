package com.example.mobilebank.utils.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AccountNameNumberText(
    textName: String,
    textNumber: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(top = 15.dp, start = 20.dp)) {
        Text(
            text = textName, style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212821),
            )
        )
        Text(
            text = " - ", style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212821),
            )
        )
        Text(
            text = textNumber, style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFF212821),
            )
        )
    }
}

@Composable
fun AccountNameNumberTextCopy(
    textName: String,
    textNumber: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        AccountNameNumberText(textName = textName, textNumber = textNumber, modifier = Modifier)
        CopyButton(onClick = onClick)
    }
}

@Composable
fun BalanceText(
    modifier: Modifier = Modifier,
    balance: String,
    isVisible: Boolean,
    eyeClick: () -> Unit,
    style: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF212821)
    )
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AutoSizeText(
            text = if (isVisible) "RP $balance" else "RP ********",
            style = style,
            modifier = Modifier.weight(3f)
        )
        EyeIconButton(onClick = eyeClick, modifier = Modifier, isVisible = isVisible)
        Spacer(modifier = modifier.width(5.dp).weight(1f))
    }
}

@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null
) {
    var textStyleState by remember { mutableStateOf(style) }
    var readyToDraw by remember { mutableStateOf(false) }
    Text(
        text = text,
        modifier = modifier.drawWithContent {
            if (readyToDraw) {
                drawContent()
            }
        },
        style = textStyleState,
        maxLines = maxLines,
        softWrap = false,
        textAlign = textAlign,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                textStyleState = textStyleState.copy(fontSize = textStyleState.fontSize * 0.9)
            } else {
                readyToDraw = true
            }
        }
    )
}

@Preview(showBackground = true, name = "Saving Card")
@Composable
fun AccountNameNumberTextPreview() {
    AccountNameNumberTextCopy(
        textName = "greenSavng",
        textNumber = "9876543210",
        onClick = {},
        modifier = Modifier
    )
}

