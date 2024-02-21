package com.example.standardblognote.ui.Components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun Skeleton(
    modifier: Modifier = Modifier,
    style: String? = null
) {
    val pulseAlpha = animateFloatAsState(targetValue = 0.3f).value
    val backgroundColor = Color(0xFFD1D5DB)

    Box(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer(alpha = pulseAlpha )
            .background(color = backgroundColor)
    )
}