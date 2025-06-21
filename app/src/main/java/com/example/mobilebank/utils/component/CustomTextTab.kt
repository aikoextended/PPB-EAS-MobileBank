package com.example.mobilebank.utils.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextTab(
    items: List<String>,
    selectedItemIndex: Int,
    onClick: (Int) -> Unit
) {
    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        items.forEachIndexed { index, item ->
            Text(
                text = item,
                fontSize = 16.sp,
                color = if (index == selectedItemIndex) Color.White else Color.Black,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 6.dp)
                    .background(
                        color = if (index == selectedItemIndex) Color(0xFF4CAF50) else Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onClick(index) }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}
