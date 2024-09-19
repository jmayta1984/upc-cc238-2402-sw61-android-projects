package pe.edu.upc.jokescompose.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
            state.joke?.let { joke ->
                Card(modifier = Modifier.padding(8.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(joke.description)
                        Row {
                            for (i in 1..5) {
                                IconButton(onClick = {
                                    viewModel.onScoreChanged(i)
                                }) {
                                    Icon(
                                        Icons.Filled.Star,
                                        "Score $i",
                                        tint = if (i <= joke.score) Color.Red else Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }


            }
        }
    }
}