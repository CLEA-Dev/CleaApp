package com.drcmind.cleaapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CleaLogo(modifier: Modifier = Modifier, horizontal: Boolean = false) {
    if (horizontal) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            LogoIcon(size = 32.dp, iconSize = 20.dp)
            Spacer(modifier = Modifier.width(8.dp))
            LogoText(fontSize = 16.sp)
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            LogoIcon(size = 48.dp, iconSize = 32.dp)
            Spacer(modifier = Modifier.height(8.dp))
            LogoText(fontSize = 20.sp)
        }
    }
}

@Composable
private fun LogoIcon(size: androidx.compose.ui.unit.Dp, iconSize: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Eco,
            contentDescription = "Clea Logo",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
private fun LogoText(fontSize: androidx.compose.ui.unit.TextUnit) {
    Text(
        text = "CLEA",
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            letterSpacing = 1.sp
        ),
        color = MaterialTheme.colorScheme.primary
    )
}
