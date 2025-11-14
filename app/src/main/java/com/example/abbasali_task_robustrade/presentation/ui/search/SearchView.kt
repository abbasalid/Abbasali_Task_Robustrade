package com.example.abbasali_task_robustrade.presentation.ui.search

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    onSearchTriggered: (String) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    SearchBar(
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        onClearClick = {
            searchQuery = ""
            onSearchTriggered("")
        },
        onSearchTriggered = { query ->
            Log.d("Search", "Searching for $query")
            onSearchTriggered(query)
        },
        modifier = modifier.padding(top = 8.dp)
    )
}
