package com.example.wonderfulcompose.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
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
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
//        topBar = {
//            if (topBarState.value) {
//                TopAppBar(
//                    title = { TitleTopBar(name) },
//                    colors = TopAppBarDefaults.smallTopAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primaryContainer,
//                        titleContentColor = MaterialTheme.colorScheme.primary
//                    ),
//                    actions = {
//                        IconButton(onClick = { showThemeDialog.value = true }) {
//                            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
//                        }
//
//                        IconButton(onClick = { navController.navigateToAddNewCat() }) {
//                            Icon(
//                                imageVector = Icons.Default.Add,
//                                contentDescription = null,
//                            )
//                        }
//                    }
//                )
//            }
//        }
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
            AnotherLogin {
                navController.navigateToMain()
            }
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
fun AnotherLogin(navigateToMain: () -> Unit) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LargeIcon()
            LoginWithGoogleButton(navigateToMain = navigateToMain)
        }
    }
}

@Composable
fun LargeIcon() {
    Image(
        painter = painterResource(id = R.drawable.login_logo), // Replace with your large icon resource
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(250.dp)
            .clip(CircleShape)
    )
}

@Composable
fun LoginWithGoogleButton(
    mainViewModel: MainViewModel = hiltViewModel(),
    navigateToMain: () -> Unit
) {
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    val webClientId = stringResource(R.string.your_web_client_id)
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)
    val googleIdOption: GetSignInWithGoogleOption =
        GetSignInWithGoogleOption.Builder(webClientId)
            .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
    Button(
        onClick = {
            scope.launch {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = context
                    )
                    mainViewModel.handleSignIn(result)
                    navigateToMain()
                } catch (e: GetCredentialException) {
                    Log.e("TinasGoogle", "AnotherLogin", e)

                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_google),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Login with Google",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}


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