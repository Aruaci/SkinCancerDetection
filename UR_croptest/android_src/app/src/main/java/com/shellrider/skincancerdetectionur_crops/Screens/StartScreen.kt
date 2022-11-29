package com.shellrider.skincancerdetectionur_crops.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun StartScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("full_view_with_crop") }) {
            Text(text = "FULL VIEW WITH CROP BORDER")
        }
        Button(onClick = { navController.navigate("full_view") }) {
            Text(text = "FULL VIEW")
        }
        Button(onClick = { navController.navigate("square_view")}) {
            Text(text = "SQUARE VIEW")
        }
        Button(onClick = { navController.navigate("original_view_with_crop") }) {
            Text(text = "ORIGINAL VIEW WITH CROP BORDER")
        }
        Button(onClick = { navController.navigate("original_view") }) {
            Text(text = "ORIGINAL VIEW")
        }
    }
}