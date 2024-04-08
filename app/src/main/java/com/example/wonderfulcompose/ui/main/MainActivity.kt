package com.example.wonderfulcompose.ui.main

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.wonderfulcompose.R
import com.example.wonderfulcompose.components.BasicDialogBox
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.components.ThemeDialogBox
import com.example.wonderfulcompose.ui.add.AddNewCatScreen
import com.example.wonderfulcompose.ui.profile.CatItem
import com.example.wonderfulcompose.ui.profile.CatProfileScreen
import com.example.wonderfulcompose.ui.theme.WonderfulComposeTheme
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

//    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

//        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // [END config_signin]

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth

        setContent {
            WonderfulComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home(stringResource(R.string.cat_adoption))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(name: String) {
    val navController = rememberNavController()
    val topBarState = rememberSaveable { (mutableStateOf(true)) }
    val showBasicDialog = rememberSaveable { mutableStateOf(false) }
    val showThemeDialog = rememberSaveable { mutableStateOf(false) }
    HandleBackStackEntry(topBarState, navController.currentBackStackEntryAsState())
    Scaffold(
        topBar = {
            if (topBarState.value) {
                TopAppBar(
                    title = { TitleTopBar(name) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    actions = {
                        IconButton(onClick = { showThemeDialog.value = true }) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                        }

                        IconButton(onClick = { navController.navigateToAddNewCat() }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                            )
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        MainNavHost(innerPadding, navController)
        OpenDialogBox(showBasicDialog)
        ChangeTheme(showThemeDialog)
    }
}

@Composable
fun HandleBackStackEntry(
    topBarState: MutableState<Boolean>,
    navBackStackEntry: State<NavBackStackEntry?>
) {
    when (navBackStackEntry.value?.destination?.route) {
        Main.route -> {
            topBarState.value = true
        }

        CatProfile.routWithArgs -> {
            topBarState.value = false
        }
    }
}

@Composable
fun MainNavHost(innerPadding: PaddingValues, navController: NavHostController) {
    var isLoading by remember { mutableStateOf(true) }
    //todo-later polish this
    LaunchedEffect(key1 = true) {
        delay(2000)
        isLoading = false
    }

    NavHost(
        navController = navController,
        startDestination = Login.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Login.route) {
            AnotherLogin()
        }
        composable(route = Main.route) {
            MainBody(isLoading = isLoading) { index ->
                navController.navigateToCatProfile(index)
            }
        }
        composable(
            route = CatProfile.routWithArgs,
            arguments = CatProfile.arguments
        ) { navBackStackEntry ->
            val catItemIndex = navBackStackEntry.arguments?.getInt(CatProfile.catTypeArg)
            catItemIndex?.let {
                CatProfileScreen(it)
            }
        }

        composable(route = AddCat.route) {
            AddNewCatScreen {
                navController.navigateUp()
            }
        }
    }

}

@Composable
fun TitleTopBar(name: String) {
    Text(text = name)
}

@Composable
fun AnotherLogin( mainViewModel: MainViewModel = hiltViewModel()) {
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    val webClientId = stringResource(R.string.your_web_client_id)
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)
    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(webClientId)
        .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()


    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            scope.launch {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = context
                    )
                    mainViewModel.handleSignIn(result)
                } catch (e: GetCredentialException) {
                    Log.e("TinasGoogle", "AnotherLogin", e)

                }
            }

        }) {
            Text(stringResource(R.string.login_with_google))
        }
    }

}



//
//@Composable
//fun LoginWithGoogle(navigateToMain: (FirebaseUser) -> Unit) {
//    val mainViewModel: MainViewModel = hiltViewModel()
//    val webClientId = stringResource(R.string.your_web_client_id)
//    val context = LocalContext.current
//    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken(webClientId)
//        .requestEmail()
//        .build()
//
//    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)
//    val auth: FirebaseAuth = Firebase.auth
//    val RC_SIGN_IN_CODE = 100
//    val signInIntent = googleSignInClient.signInIntent
//    val pendingIntent =
//        PendingIntent.getActivity(
//            context,
//            RC_SIGN_IN_CODE,
//            signInIntent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//
//    val launcher =
//        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                try {
//                    // Google Sign In was successful, authenticate with Firebase
//                    val account = task.result!!
//                    Log.d("TinasGoogle", "firebaseAuthWithGoogle:" + account.id)
//                    firebaseAuthWithGoogle(
//                        auth = auth,
//                        idToken = account.idToken!!,
//                        navigateToMain = { user ->
//                            mainViewModel.saveUser(user)
//                            navigateToMain(user)
//                        }
//                    )
//                } catch (e: ApiException) {
//                    // Google Sign In failed, update UI appropriately
//                    Log.w("TinasGoogle", "Google sign in failed", e)
//                }
//            } else {
//                Log.d("TinasGoogle", "Google sign in failed$result")
//            }
//        }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Button(onClick = {
//            launcher.launch(
//                IntentSenderRequest.Builder(pendingIntent)
//                    .build()
//            )
//        }) {
//            Text(stringResource(R.string.login_with_google))
//        }
//    }
//}
//
//private fun firebaseAuthWithGoogle(
//    auth: FirebaseAuth,
//    idToken: String,
//    navigateToMain: (FirebaseUser) -> Unit
//) {
//    val credential = GoogleAuthProvider.getCredential(idToken, null)
//    auth.signInWithCredential(credential)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
//                Log.d("TinasGoogle", "signInWithCredential:success")
//                val user = auth.currentUser
//                user?.let { navigateToMain(user) }
//            } else {
//                // If sign in fails, display a message to the user.
//                Log.w("TinasGoogle", "signInWithCredential:failure", task.exception)
////                updateUI(null)
//            }
//        }
//}

@Composable
fun MainBody(
    mainViewModel: MainViewModel = hiltViewModel(),
    isLoading: Boolean,
    onItemClick: (catItemIndex: Int) -> Unit
) {
    LaunchedEffect(key1 = Unit)
    {
        mainViewModel.getCats()
    }
    val cats = mainViewModel.catsFlow.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Hi " + mainViewModel.getUserName() + "!"
                )
            }
        }
        items(
            count = cats.itemCount,
            key = cats.itemKey { it.hashCode() },
            contentType = cats.itemContentType { "contentType" }
        ) { index ->
            val cat = cats[index]
            cat?.let {
                CatItem(isLoading, cat) {
                    onItemClick.invoke(index)
                }
            }
        }
    }
}


@Composable
fun OpenDialogBox(isDialogVisible: MutableState<Boolean>) {
    //todo-later add cat function instead
    if (isDialogVisible.value) {
        BasicDialogBox(
            dialogTitle = stringResource(id = R.string.unsaved_changes_dialog_title),
            dialogText = stringResource(id = R.string.unsaved_changes_dialog_text),
            positiveButtonText = stringResource(id = R.string.dialog_button_yes),
            negativeButtonText = stringResource(id = R.string.dialog_button_no),
            onConfirmation = { isDialogVisible.value = false }) {
            isDialogVisible.value = false
        }
    }
}

@Composable
fun ChangeTheme(isDialogVisible: MutableState<Boolean>) {
    //todo-later add logic for theme
    if (isDialogVisible.value) {
        ThemeDialogBox(
            itemList = listOf("Light", "Dark", "System Default"),
            currentlySelectedItem = "Dark",
            { Log.i("ChangeTheme", "Selected theme is $it") }) {
            isDialogVisible.value = false
        }
    }
}


@PreviewUtil()
@Composable
fun GreetingPreview() {
    WonderfulComposeTheme {
        Home(stringResource(R.string.cat_adoption))
    }
}