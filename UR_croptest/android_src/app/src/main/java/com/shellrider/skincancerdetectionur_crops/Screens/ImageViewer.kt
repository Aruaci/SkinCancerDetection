package com.shellrider.skincancerdetectionur_crops.Screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter

@Composable
fun ImageViewer(imagePath: String) {
    Image(painter = rememberImagePainter(imagePath), contentDescription = "Cached Image")
}