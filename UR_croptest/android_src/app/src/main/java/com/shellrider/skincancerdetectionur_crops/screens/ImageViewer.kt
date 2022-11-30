package com.shellrider.skincancerdetectionur_crops.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.shellrider.skincancerdetectionur_crops.filesystem.writeImageToStorage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ImageViewer(navController: NavController) {
    val context = LocalContext.current
    val imageFile = File(context.cacheDir, "cachedImage")

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = rememberImagePainter(imageFile),
        contentDescription = "Cached Image"
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(64.dp).align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    writeImageToStorage(context, imageFile, SimpleDateFormat("ddMMyy-hhmmss.SSS").format(
                        Date()
                    ) + ".jpg")
                    navController.navigate("start")
                }
            ) {
                Text(text = "Okay")
            }
            Button(
                onClick = {
                    writeImageToStorage(context, imageFile, SimpleDateFormat("ddMMyy-hhmmss.SSS").format(
                        Date()
                    ) + ".jpg")
                    navController.navigate("start")
                }
            ) {
                Text(text = "Reject")
            }
        }


    }

}