package com.mhmtn.satellites2.presentation.satellite_detail.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun DetailRow(text:String, detail1: Int, detail2: Int?) {
    val text1 = buildAnnotatedString {
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Bold
        )
        ){
            append(text)
        }
        append(detail1.toString())
        if (detail2 == null) {
            append("")
        }else{
            append("/")
            append(detail2.toString())
        }
    }
    Text(text = text1)
}
