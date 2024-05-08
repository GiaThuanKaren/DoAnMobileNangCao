package com.example.standardblognote.ui.Components.RichText

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.standardblognote.ui.theme.colorSecondary
import com.example.standardblognote.ui.theme.primaryColor
import com.example.standardblognote.ui.theme.textColorPrimary

@Composable
fun ToolBoxIcon(icon: ImageVector, contentDescription : String,
                onClick : () -> Unit, isHighlighted : Boolean) {

    Column(
        Modifier
            .height(34.dp)
            .width(34.dp)
            .background(
                color = when (isHighlighted) {
                    true -> primaryColor
                    else -> colorSecondary
                },
                shape = RoundedCornerShape(90.dp)
            )
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            icon, contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = textColorPrimary
        )
    }
}