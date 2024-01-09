package com.example.wonderfulcompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderfulcompose.data.models.CatsContract
import com.example.wonderfulcompose.data.models.CatsContract.*
import com.example.wonderfulcompose.data.repository.CatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
   private val catsRepository: CatRepository
) : ViewModel(), CatsContract {

    private val mutableState = MutableStateFlow(State())
    override val state: StateFlow<State> =
        mutableState.asStateFlow()
    override val action: SharedFlow<Action>
        get() = TODO("Not yet implemented")

    override fun intention(intent: Intention) {
        TODO("Not yet implemented")
    }


    fun intentToAction(intent: Intention): Action {
        return when (intent) {
            is Intention.LoadAllCats -> Action.LoadCats(false)
            is Intention.LoadFavorites -> Action.LoadCats(true)
            is Intention.ClickFavorite -> if (intent.isChecked) Action.AddToFavorite(intent.cat)
                else Action.RemoveFromFavorite(intent.cat)
        }
    }

    fun handleAction(action: Action) {
        viewModelScope.launch {
            when (action) {
                is Action.LoadCats -> {
                    catsRepository.loadCats(action.isFavorite).collect {cats->
                        mutableState.update {
                           it.copy(cats = cats)
                        }
                    }
                }
                is Action.AddToFavorite -> {
                    TODO("Not yet implemented")
                }
                is Action.RemoveFromFavorite -> {
                    TODO("Not yet implemented")
                }
            }
        }
    }

}