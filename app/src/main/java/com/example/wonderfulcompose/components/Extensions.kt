package com.example.wonderfulcompose.components

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun NavController.navigateSingleTopWithFirstDestinationTo(route: String) = this.navigate(route = route) {
    popUpTo(this@navigateSingleTopWithFirstDestinationTo.graph.findStartDestination().id)
    launchSingleTop = true
    restoreState = true
}
fun NavController.navigateSingleTopTo(route: String) = this.navigate(route = route) {
    launchSingleTop = true
}

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}