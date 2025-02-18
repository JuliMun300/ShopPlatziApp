package com.example.platzishop.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Composable
fun CreateUserLogin(NavController: NavHostController) {

    val auth = Firebase.auth
    val context = LocalContext.current
    background()
    PaintCreateUser(context, NavController, auth)
}

//FUNCION QUE PINTA EL EMAIL Y CONTRASEÑA Y EL NOMBRE DE USUARIO
@Composable
fun PaintCreateUser(context: Context, navController: NavHostController, auth: FirebaseAuth) {

    var Username by remember { mutableStateOf("") }
    var Email by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        //CAMPO PARA NOMBRE DE USUARIO
        OutlinedTextField(
            value = Username,
            onValueChange = { Username = it },
            label = { Text("Username") },
            modifier = Modifier.padding(8.dp)
        )

        //CAMPO PARA EMAIL
        OutlinedTextField(
            value = Email,
            onValueChange = { Email = it },
            label = { Text("Email") },
            modifier = Modifier.padding(8.dp)
        )

        //CAMPO PARA CONTRASEÑA
        OutlinedTextField(
            value = Password,
            onValueChange = { Password = it },
            label = { Text("Password") },
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        //BOTON CREAR USUARIO
        Button(modifier = Modifier
            .height(35.dp)
            .width(280.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.Gray
            ),
            onClick = { verificarDatos(Username, Email, Password, context, navController, auth) }) {
            Text(
                text = "Crear Usuario",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

//FUNCION PARA VERIFICAR LOS DATOS
fun verificarDatos(
    username: String,
    email: String,
    password: String,
    context: Context,
    navController: NavHostController,
    auth: FirebaseAuth
) {

    if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
        AñadirAutenticacion(username, email, password, auth, context)
        navController.navigate("imageUser_Screen")
    } else {
        Toast.makeText(context, "Faltan Datos", Toast.LENGTH_SHORT).show()
    }
}

//FUNCION PARA AÑADIR LOS DATOS A LA BASE DE DATOS DE AUTENTICACION
fun AñadirAutenticacion(
    username: String,
    email: String,
    password: String,
    auth: FirebaseAuth,
    context: Context
) {

    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(context, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
        }
    }.addOnFailureListener {
        Toast.makeText(context, "Algo salio mal", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showSystemUi = true)
@Composable
fun CrateuserPreview() {
    val navcontroller = rememberNavController()
    CreateUserLogin(navcontroller)
}