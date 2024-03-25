package com.example.wonderfulcompose.ui.main

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.wonderfulcompose.components.navigateSingleTopTo
import com.example.wonderfulcompose.data.models.CatPresenter

interface CatDestinations{
    val route:String
}

object CatProfile:CatDestinations{
    override val route: String = "cat_profile"
    const val catTypeArg = "cat_item"
    val routWithArgs = "$route/{$catTypeArg}"
    val arguments =  listOf(navArgument(catTypeArg) {
        type = NavType.IntType
    })
}
object Main:CatDestinations{
    override val route: String = "main"
}

object AddCat: CatDestinations {
    override val route: String = "add_cat"
}

object Login: CatDestinations {
    override val route: String = "login_with_google"
}

fun NavHostController.navigateToCatProfile(catIndex: Int) =
    this.navigateSingleTopTo("${CatProfile.route}/$catIndex")

fun NavHostController.navigateToAddNewCat() =
    this.navigateSingleTopTo(AddCat.route)