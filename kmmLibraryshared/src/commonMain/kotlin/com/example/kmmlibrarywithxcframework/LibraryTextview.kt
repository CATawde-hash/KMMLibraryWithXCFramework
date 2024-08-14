package com.example.kmmlibrarywithxcframework

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
public fun LibraryGreetingView(text: String) {
    Text(text = text, color = Color.Red, modifier = Modifier.background(Color.White).size(200.dp). padding(36.dp).border(4.dp,
        Color.Cyan))
}


