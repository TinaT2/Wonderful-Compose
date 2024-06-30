package com.example.wonderfulcompose.ui.add

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wonderfulcompose.R
import com.example.wonderfulcompose.components.BasicRadioButton
import com.example.wonderfulcompose.components.DropdownMenu
import com.example.wonderfulcompose.data.fake.breedList
import com.example.wonderfulcompose.data.fake.catList
import com.example.wonderfulcompose.data.fake.genders
import com.example.wonderfulcompose.data.models.CatPresenter
import com.example.wonderfulcompose.ui.theme.md_theme_light_primary

@Composable
fun AddNewCatScreen(
    onSubmitted: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            val name = remember { mutableStateOf("") }
            val age = remember { mutableStateOf("") }
            val bio = remember { mutableStateOf("") }
            var selectedIndex by remember { mutableIntStateOf(-1) }
            var selectedBreed by remember { mutableStateOf("") }
            var selectedGender by remember { mutableStateOf("") }


            OutlinedTextField(
                label = stringResource(id = R.string.label_new_cat_name),
                textValue = name,
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                label = stringResource(id = R.string.label_new_cat_age),
                textValue = age,
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(8.dp))

            DropdownMenu(
                label = stringResource(id = R.string.label_new_cat_breed),
                items = breedList,
                selectedIndex = selectedIndex,
                onItemSelected = { index, item ->
                    selectedIndex = index
                    selectedBreed = item
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            genders.forEach { gender ->
                BasicRadioButton(
                    itemTitle = gender,
                    isItemSelected = selectedGender == gender
                ) { selectedGender = it }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.height(300.dp),
                label = stringResource(id = R.string.label_new_cat_bio),
                textValue = bio,
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                val newCat = CatPresenter(
                    title = name.value,
                    avatar = "",
                    age = age.value,
                    breed = selectedBreed,
                    gender = selectedGender,
                    bio = bio.value,
                    createdAt = "",
                    colorId = 0
                )
                catList.add(newCat)
                onSubmitted()
            }
            ) {
                Text(text = stringResource(id = R.string.button_submit))
            }
        }
    }
}


@Composable
fun OutlinedTextField(
    modifier: Modifier = Modifier,
    label: String,
    textValue: MutableState<String>,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = textValue.value,
        onValueChange = { textValue.value = it },
        label = { Text(text = label) },
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = md_theme_light_primary,
            unfocusedIndicatorColor = md_theme_light_primary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Preview(showBackground = true)
@Composable
fun AddNewCatPreview() {
    AddNewCatScreen {}
}