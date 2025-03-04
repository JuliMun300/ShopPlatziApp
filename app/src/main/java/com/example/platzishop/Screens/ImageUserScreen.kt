package com.example.platzishop.Screens

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

@Composable
fun ImageUserScreen(NavController: NavHostController) {

    val auth = Firebase.auth
    val context = LocalContext.current

    background()
    Imagen(context, auth,NavController)
}

@Composable
fun Imagen(context: Context, auth: FirebaseAuth, NavController: NavHostController) {

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
        Spacer(modifier = Modifier.padding(16.dp))

        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color.Green,
            contentColor = Color.Gray
        ),
            onClick = {
                Toast.makeText(context, "Verificando imagen...", Toast.LENGTH_SHORT).show()
                if(imageUri != null){
                    Añadir_A_DB(
                        ImageUri = imageUri,
                        context = context,
                        OnSuccess = {
                            Toast.makeText(
                                context,
                                "La foto de perfil se a guardado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            NavController.navigate("Home_Screen")

                        },
                        OnFailure = {
                            Toast.makeText(
                                context,
                                "A ocurrido un error, vuelva a intentarlo",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        auth
                    )
                }else{
                    Toast.makeText(context, "No has seleccionado ninguna imagen", Toast.LENGTH_SHORT).show()
                }

            }) {
            Text("verificar")
        }
    }
}

fun Añadir_A_DB(
    ImageUri: Uri?,
    context: Context,
    OnSuccess: () -> Unit,
    OnFailure: () -> Unit,
    auth: FirebaseAuth
) {

    val client = OkHttpClient()
    val API_KEY = "b9b6255ea863ade5f0206389471b6d15"
    val BASE_URL = "https://api.imgbb.com/1/upload?expiration=600&key=$API_KEY"

    val ImageBase64 = uriToBase64(context, ImageUri!!)
    if (ImageBase64 == null) {
        Toast.makeText(context, "La conversion fallo", Toast.LENGTH_SHORT).show()
    }

    val requestbody = FormBody.Builder()
        .add("image", ImageBase64!!)
        .build()

    val request = Request.Builder()
        .url(BASE_URL)
        .post(requestbody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            OnFailure()
        }

        override fun onResponse(call: Call, response: Response) {

            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                val json = JSONObject(responseBody)
                val image = json.getJSONObject("data").getString("url")
                añadirImagenAfirebase(image, context, auth)
                println(responseBody)
            }
        }

        private fun añadirImagenAfirebase(image: String, context: Context, auth: FirebaseAuth) {
            val db = Firebase.firestore
            val user = auth.currentUser

            if (user != null) {

                val imagen = mapOf(
                    "url" to image
                )

                db.collection("users").document(user.uid).update(imagen).addOnSuccessListener {
                    OnSuccess()
                }.addOnFailureListener {
                    OnFailure()
                }
            }
        }
    })
}

fun uriToBase64(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        inputStream?.copyTo(byteArrayOutputStream)
        inputStream?.close()
        val byteArray = byteArrayOutputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


@Preview(showSystemUi = true)
@Composable
fun ImagePreview() {
    val navhost = rememberNavController()
    ImageUserScreen(navhost)
}