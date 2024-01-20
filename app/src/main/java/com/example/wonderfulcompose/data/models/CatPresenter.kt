package com.example.wonderfulcompose.data.models

import androidx.annotation.IntRange
import com.example.wonderfulcompose.components.UnidirectionalViewModel

data class CatPresenter(
    val name: String,
    val avatar: String,
    val age: String,
    val breed: String,
    val gender: String,
    val bio: String,
    val createdAt: String,
    val isFavorite: Boolean,
    @IntRange(from = 0, to = 100) val playful: Int = (0..100).random()
)

interface CatsContract :
    UnidirectionalViewModel<CatsContract.State, CatsContract.Action,
            CatsContract.Intention > {

    data class State(
        val loading: Boolean = false,
        val cats: MutableList<CatPresenter> = mutableListOf(),
        val error: Exception? = null,
        val showFavoriteList: Boolean = false
    )

    sealed class Intention {
        object LoadAllCats : Intention()
        object LoadFavorites : Intention()
        data class ClickFavorite(val cat: CatPresenter, val isChecked: Boolean) : Intention()
//        object OnRefresh: Intention()
//        object OnBackPressed : Intention()
//        data class ShowToast(val message: String) : Intention()
    }

    sealed class Action {
        data class LoadCats(val isFavorite: Boolean) : Action()
        data class AddToFavorite(val cat: CatPresenter) : Action()
        data class RemoveFromFavorite(val cat: CatPresenter) : Action()
//        object OnBackPressed : Action()
//        data class ShowToast(val message: String) : Action()
    }
}
