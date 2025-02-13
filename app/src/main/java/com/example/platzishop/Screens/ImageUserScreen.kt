package com.example.platzishop.Screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImageUserScreen() {

    background()
    Imagen()
}

@Composable
fun Imagen() {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    val scale by animateFloatAsState(
        targetValue = if (imageUri != null) 1.2f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "scale"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        imageUri.let { uri: Uri? ->
            AnimatedVisibility(visible = imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Imagen seleccionada",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Green, RoundedCornerShape(16.dp))

                )
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color.Green,
            contentColor = Color.Gray
        ), onClick = { launcher.launch("image/*") }) {
            Text("Seleccionar imagen")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ImagePreview() {
    ImageUserScreen()
}