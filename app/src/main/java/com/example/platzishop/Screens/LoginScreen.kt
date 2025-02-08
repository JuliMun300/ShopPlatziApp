package com.example.platzishop.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.platzishop.R

@Composable
fun LoginScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    background()
    PaintLogin()


}

@Composable
fun PaintLogin() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagenLogo()
        Spacer(modifier = Modifier.padding(5.dp))
        EmailAndPassword()
    }
}

@Composable
fun BotonIniciarSesion() {
    TODO("Not yet implemented")
}

//FUNCION QUE PINTA EL EMAIL Y CONTRASEÑA
@Composable
fun EmailAndPassword() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    //CAMPO PARA EMAIL
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Email") },
        modifier = Modifier.padding(8.dp)

    )
    //Spacer(modifier = Modifier.padding(5.dp))

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
        onClick = { IniciarSesion(email,password) }) {
        Text(
            text = "Iniciar Sesión",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
    Spacer(modifier = Modifier.padding(8.dp))

    //BOTON CREAR USUARIO
    Button(modifier = Modifier
        .height(35.dp)
        .width(280.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Green,
            contentColor = Color.Gray
        ),
        onClick = { IniciarSesion(email,password) }) {
        Text(
            text = "Crear Usuario",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }

}

fun IniciarSesion(email: String, password: String) {

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
    LoginScreen()
}
