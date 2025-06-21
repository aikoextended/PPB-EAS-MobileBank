package com.example.mobilebank.utils.helper

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class ThousandSeparatorTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text

        // Bersihkan titik
        val digitsOnly = originalText.replace(".", "").take(15)

        // Format angka
        val formatted = digitsOnly.toLongOrNull()?.let {
            val formatter = DecimalFormat("#,###", DecimalFormatSymbols(Locale("in", "ID")))
            formatter.format(it)
        } ?: ""

        // Offset mapping (agar kursor tidak loncat)
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var extra = 0
                for (i in 0 until offset) {
                    if ((digitsOnly.length - i) % 3 == 0 && i != 0) {
                        extra++
                    }
                }
                return offset + extra
            }

            override fun transformedToOriginal(offset: Int): Int {
                var extra = 0
                var i = 0
                var t = 0
                while (i < digitsOnly.length && t < offset) {
                    if ((digitsOnly.length - i) % 3 == 0 && i != 0) {
                        t++
                        extra++
                    }
                    i++
                    t++
                }
                return offset - extra
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}
