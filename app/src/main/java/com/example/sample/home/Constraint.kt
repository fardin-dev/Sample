package com.example.sample.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun SimpleConstraintLayout() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        // Create references for the composables
        val (text1, text2, button) = createRefs()

        Text(
            text = "Hello, Mukesh!",
            modifier = Modifier.constrainAs(text1) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = "Aligned below Text1",
            modifier = Modifier.constrainAs(text2) {
                top.linkTo(text1.bottom, margin = 8.dp)
                start.linkTo(text1.start)
            }
        )

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(text2.bottom, margin = 16.dp)
                start.linkTo(parent.start)
            }
        ) {
            BasicText("Click Me")
        }
    }
}