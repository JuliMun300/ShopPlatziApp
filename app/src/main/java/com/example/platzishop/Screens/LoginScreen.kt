package com.example.platzishop.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.platzishop.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Composable
fun LoginScreen(NavController: NavHostController) {

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = Firebase.auth

    background()
    PaintLogin(NavController, context, auth)
}

@Composable
fun PaintLogin(NavController: NavHostController, context: Context, auth: FirebaseAuth) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagenLogo()
        Spacer(modifier = Modifier.padding(5.dp))
        EmailAndPassword(NavController, context, auth)
    }
}

//FUNCION QUE PINTA EL EMAIL Y CONTRASEÑA
@Composable
fun EmailAndPassword(NavController: NavHostController, context: Context, auth: FirebaseAuth) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    //CAMPO PARA EMAIL
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Email") },
        modifier = Modifier.padding(8.dp)
    )

    //CAMPO PARA CONTRASEÑA
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password") },
        modifier = Modifier.padding(8.dp)
    )
    Spacer(modifier = Modifier.padding(10.dp))

    //BOTON INICIAR SESION
    Button(modifier = Modifier
        .height(35.dp)
        .width(280.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Green,
            contentColor = Color.Gray
        ),
        onClick = { IniciarSesion(email, password, context, auth) }) {
        Text(
            text = "Iniciar Sesión",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
    Spacer(modifier = Modifier.padding(8.dp))

    //BOTON CREAR USUARIO
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append("¿No tienes una cuenta?  ")
            }

            withStyle(style = SpanStyle(color = Color.Green)) {
                append("Registrate")
            }
        },
        modifier = Modifier.clickable {
            CrearUsuario(NavController)
        }
    )
}

//FUNCION PARA IR A LA PANTALLA DE CREAR USUARIO
fun CrearUsuario(NavController: NavHostController) {
    NavController.navigate("CreateUser_Screen")
}

//FUNCION PARA INICIAR SESION
fun IniciarSesion(email: String, password: String, context: Context, auth: FirebaseAuth) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        AutenticacionLogin(email, password, context, auth)
    } else {
        Toast.makeText(context, "Faltan Datos", Toast.LENGTH_SHORT).show()
    }
}

//FUNCION PARA VERIFICAR LOS DATOS A LA BASE DE DATOS DE AUTENTICACION
fun AutenticacionLogin(email: String, password: String, context: Context, auth: FirebaseAuth) {

    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(context, "Inicio correcto", Toast.LENGTH_SHORT).show()
        }
    }.addOnFailureListener {
        Toast.makeText(context, "Algo salio mal", Toast.LENGTH_SHORT).show()
    }
}

//FUNCION QUE METE UNA IMAGEN DE LOGO
@Composable
fun ImagenLogo() {

    //IMAGEN DEL LOGO
    Image(
        painter = painterResource(R.drawable.logo_platzi),
        contentDescription = "Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(200.dp)
    )
}

//FUNCION DEL FONDO DEL LOGIN
@Composable
fun background() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}

@Preview(showSystemUi = true)
@Composable
fun preview() {
    val NavController = rememberNavController()
    LoginScreen(NavController)
}
