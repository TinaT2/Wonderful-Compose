package com.example.wonderfulcompose.ui.main

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wonderfulcompose.data.SharedPreferencesPersistor
import com.example.wonderfulcompose.data.models.CatPresenter
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val catsRepository: CatsRepository,
    private val sharedPreferencesPersistor: SharedPreferencesPersistor,
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<PagingData<CatPresenter>>(PagingData.empty())
    val catsFlow: StateFlow<PagingData<CatPresenter>> = _catsFlow

    fun getCats() {
        viewModelScope.launch {
            catsRepository.getCats().cachedIn(viewModelScope).collect {
                _catsFlow.value = it
            }
        }


    }



    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential
        val TAG = "TinasGoogle"
        when (credential) {

            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        Log.d("TinasGoogle", "firebaseAuthWithGoogle:" + googleIdTokenCredential)

                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }

    fun saveUser(user: FirebaseUser) {
        sharedPreferencesPersistor.save(
            key = SharedPreferencesPersistor.USER_NAME,
            value = user.displayName
        )
    }

    fun getUserName() =
        sharedPreferencesPersistor.get(key = SharedPreferencesPersistor.USER_NAME)

}