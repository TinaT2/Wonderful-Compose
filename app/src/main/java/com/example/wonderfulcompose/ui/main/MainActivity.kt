package com.example.wonderfulcompose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wonderfulcompose.R
import com.example.wonderfulcompose.components.BasicDialogBox
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.data.fake.catList
import com.example.wonderfulcompose.ui.profile.CatItem
import com.example.wonderfulcompose.ui.profile.CatProfileScreen
import com.example.wonderfulcompose.ui.theme.WonderfulComposeTheme
import kotlinx.coroutines.delay

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
    val showDialog = rememberSaveable { mutableStateOf(false) }
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
                        IconButton(onClick = { showDialog.value = true }) {
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
        OpenDialogBox(showDialog)
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
    LaunchedEffect(key1 = true) {
        delay(2000)
        isLoading = false
    }

    NavHost(
        navController = navController,
        startDestination = Main.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Main.route) {
            MainBody(isLoading) { index ->
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
    }

}

@Composable
fun TitleTopBar(name: String) {
    Text(text = name)
}

@Composable
fun MainBody(isLoading: Boolean, onItemClick: (catItemIndex: Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(catList) { cat ->
            CatItem(isLoading, cat) { selectedCat ->
                onItemClick.invoke(catList.indexOf(selectedCat))
            }
        }
    }
}


@Composable
fun OpenDialogBox(isDialogVisible: MutableState<Boolean>) {
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


@PreviewUtil()
@Composable
fun GreetingPreview() {
    WonderfulComposeTheme {
        Home(stringResource(R.string.cat_adoption))
    }
}