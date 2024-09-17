package pe.edu.upc.jokescompose.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun JokeScreen(viewModel: JokeViewModel) {
    val state = viewModel.state.value

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedButton(onClick = {
                viewModel.getJoke()
            }) {
                Text("Get joke!")
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            state.joke?.let {
                Text(it.description)
                Row {

                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Star, "Score")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Star, "Score")
                    }
                }
            }
        }
    }
}