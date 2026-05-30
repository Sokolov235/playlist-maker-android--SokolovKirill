package com.example.myapplication.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.components.TrackListItem
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.view_model.SearchState
import com.example.myapplication.ui.view_model.SearchViewModel
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit
) {

    val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.factory())
    val state by viewModel.state.collectAsState()

    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Поиск") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModel.search(text)
                        }
                    )
                },

                trailingIcon = {
                    if (text.isNotEmpty()) {
                        IconButton(onClick = { text = "" }) {
                            Icon(Icons.Default.Clear, null)
                        }
                    }
                },

                placeholder = { Text("Поиск") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (state) {

                is SearchState.Initial -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Введите строку для поиска")
                    }
                }

                is SearchState.Searching -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is SearchState.Success -> {
                    val list = (state as SearchState.Success).foundList

                    LazyColumn {
                        items(list) {
                            TrackListItem(it)
                            HorizontalDivider()
                        }
                    }
                }

                is SearchState.Fail -> {
                    val error = (state as SearchState.Fail).error

                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Ошибка: $error")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchPreview() {
    MyApplicationTheme() {
        Surface() {
            SearchScreen(
                onBackClick = {}
            )
        }
    }
}
