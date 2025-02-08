package com.example.platzishop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.platzishop.Screens.CreateUserLogin
import com.example.platzishop.Screens.LoginScreen

@Composable
fun AppNavigation() {
  val NavController = rememberNavController()

  NavHost(navController = NavController, startDestination = AppScreans.Login_Screen.route){
      composable(route = AppScreans.Login_Screen.route){
          LoginScreen(NavController)
      }
      composable(route = AppScreans.CreateUser_Screen.route){
          CreateUserLogin(NavController)
      }
  }
}