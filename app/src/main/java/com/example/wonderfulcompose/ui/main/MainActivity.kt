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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wonderfulcompose.ui.profile.PeopleItem
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.R
import com.example.wonderfulcompose.data.fake.catList
import com.example.wonderfulcompose.ui.theme.WonderfulComposeTheme

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TitleTopBar(name) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        MainBody("Hi Dear Compose App", innerPadding)
    }
}

@Composable
fun TitleTopBar(name: String) {
    Text(text = name)
}

@Composable
fun MainBody(text: String, paddingValues: PaddingValues) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(catList) { cat ->
            PeopleItem(cat)
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