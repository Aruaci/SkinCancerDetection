package com.shellrider.skincancerdetectionur_crops.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.shellrider.minipainter.camera.CameraCapture
import com.shellrider.minipainter.camera.CameraPermission

@Composable
fun SquareView() {

    @Composable
    fun overlay(){
        Canvas(modifier = Modifier.fillMaxSize()){
            drawRect(
                color = Color.Black,
                topLeft = Offset(0f,0f),
                size = Size(size.width,size.height/2-size.width/2)
            )
            drawRect(
                color = Color.Black,
                topLeft = Offset(0f,size.height-size.width/2),
                size = Size(size.width,size.height/2-size.width/2)
            )
        }
    }

    CameraPermission {
        CameraCapture(cameraOverlay = { overlay() })
    }
}