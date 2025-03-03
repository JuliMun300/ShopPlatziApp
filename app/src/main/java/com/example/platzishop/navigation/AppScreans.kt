package com.example.platzishop.navigation

 sealed class AppScreans(val route:String) {
     object Login_Screen:AppScreans("Login_Screen")
     object CreateUser_Screen:AppScreans("CreateUser_Screen")
     object ImageUser_Screen:AppScreans("ImageUser_Screen")
     object Home_Screen:AppScreans("Home_Screen")
}