package com.shellrider.skincancerdetectionur_crops.Screens

import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import com.shellrider.minipainter.camera.CameraCapture
import com.shellrider.minipainter.camera.CameraPermission
import com.shellrider.skincancerdetectionur_crops.Screens.overlays.CropBorderOverlay

@Composable
fun FullView(){
    CameraPermission {
        CameraCapture(
            previewScaleType = PreviewView.ScaleType.FILL_CENTER
        )
    }
}