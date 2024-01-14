package com.example.wonderfulcompose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

fun NavController.navigateSingleTopTo(route: String) = this.navigate(route = route) {
    popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id)
    launchSingleTop = true
    restoreState = true
}

@Composable
inline fun <reified STATE, ACTION, INTENT> use(
    viewModel: UnidirectionalViewModel<STATE, ACTION, INTENT>,
): StateDispatchEffect<STATE, ACTION, INTENT> {
    //todo collect as state with lifecycle
    val state by viewModel.state.collectAsState()

    val dispatch: (INTENT) -> Unit = { intent ->
        viewModel.intention(intent)
    }

    return StateDispatchEffect(
        state = state,
        dispatch = dispatch,
        effectFlow = viewModel.action,
    )
}

data class StateDispatchEffect<STATE, ACTION, INTENT>(
    val state: STATE,
    val dispatch: (INTENT) -> Unit,
    val effectFlow: SharedFlow<ACTION>,
)

@Suppress("ComposableNaming")
@Composable
fun <T> SharedFlow<T>.collectInLaunchedEffect(function: suspend (value: T) -> Unit) {
    val sharedFlow = this
    LaunchedEffect(key1 = sharedFlow) {
        sharedFlow.collectLatest(function)
    }
}