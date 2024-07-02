package com.example.wonderfulcompose.ui.main

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.wonderfulcompose.components.navigateSingleTopTo
import com.example.wonderfulcompose.components.navigateSingleTopWithFirstDestinationTo

interface CatDestinations {
    val route: String
}

object CatProfile : CatDestinations {
    override val route: String = "cat_profile"
    const val catTypeArg = "cat_item"
    val routWithArgs = "$route/{$catTypeArg}"
    val arguments = listOf(navArgument(catTypeArg) {
        type = NavType.IntType
    })
}

object Main : CatDestinations {
    override val route: String = "main"
}

object AddCat : CatDestinations {
    override val route: String = "add_cat"
}

object Login : CatDestinations {
    override val route: String = "login_with_google"
}

object ColorCategory : CatDestinations {
    override val route: String = "color_category"
    const val categoryTypeArg = "category_item"
    val routWithArgs = "$route/{$categoryTypeArg}"
    val arguments = listOf(navArgument(categoryTypeArg) {
        type = NavType.IntType
    })
}

fun NavHostController.navigateToCatProfile(catIndex: Int) =
    this.navigateSingleTopTo("${CatProfile.route}/$catIndex")

fun NavHostController.navigateToAddNewCat() =
    this.navigateSingleTopWithFirstDestinationTo(AddCat.route)

fun NavHostController.navigateToMain() =
    this.navigateSingleTopWithFirstDestinationTo(Main.route)

fun NavHostController.navigateToColorCategory(colorId: Int?) =
    colorId?.let { this.navigateSingleTopTo("${ColorCategory.route}/$colorId") }
        ?: this.navigateSingleTopTo(ColorCategory.route)