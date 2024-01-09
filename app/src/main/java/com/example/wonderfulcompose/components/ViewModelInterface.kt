package com.example.wonderfulcompose.components

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface UnidirectionalViewModel<STATE, ACTION, INTENT> {
    val state: StateFlow<STATE>
    val action: SharedFlow<ACTION>
    fun intention(intent: INTENT)
}