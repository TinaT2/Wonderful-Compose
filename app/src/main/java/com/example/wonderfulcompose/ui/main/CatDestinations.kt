package com.example.wonderfulcompose.ui.main

interface CatDestinations{
    val route:String
}

object CatProfile:CatDestinations{
    override val route: String = "cat_profile"
}
object Main:CatDestinations{
    override val route: String = "main"
}