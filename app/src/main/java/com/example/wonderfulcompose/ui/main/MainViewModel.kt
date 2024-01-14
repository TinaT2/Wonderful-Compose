package com.example.wonderfulcompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderfulcompose.data.models.CatPresenter
import com.example.wonderfulcompose.data.models.CatsContract
import com.example.wonderfulcompose.data.models.CatsContract.*
import com.example.wonderfulcompose.data.repository.CatRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
   private val catsRepository: CatRepository
) : ViewModel(), CatsContract {

    init {
        handleAction()
    }

    private val mutableState = MutableStateFlow(State())
    override val state: StateFlow<State> = mutableState.asStateFlow()
    private val mutableAction: MutableSharedFlow<Action> = MutableSharedFlow()
    override val action: SharedFlow<Action> = mutableAction.asSharedFlow()
    override fun intention(intent: Intention) {
        viewModelScope.launch {
            mutableAction.emit( when (intent) {
                is Intention.LoadAllCats -> Action.LoadCats(false)
                is Intention.LoadFavorites -> Action.LoadCats(true)
                is Intention.ClickFavorite -> if (intent.isChecked) Action.AddToFavorite(intent.cat)
                else Action.RemoveFromFavorite(intent.cat)
            })
        }

    }

    private fun handleAction() {
        viewModelScope.launch {
            action.collect{action->
                when(action){
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

    //TODO("Not yet implemented")
//    fun Result<List<CatPresenter>>.reduce(isSearchMode: Boolean = false): State {
//        return when (this) {
//            is Result.Success -> if (isSearchMode) HomeState.ResultSearch(data) else HomeState.ResultAllPersona(data)
//            is Result.Error -> HomeState.Exception(exception)
//            is Result.Loading -> HomeState.Loading
//        }
//    }


}