package com.example.wonderfulcompose.components

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun NavController.navigateSingleTopTo(route: String) = this.navigate(route = route) {
    popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id)
    launchSingleTop = true
    restoreState = true
}