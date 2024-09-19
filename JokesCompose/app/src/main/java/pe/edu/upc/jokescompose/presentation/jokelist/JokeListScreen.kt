package pe.edu.upc.jokescompose.presentation.jokelist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun JokeListScreen(viewModel: JokeListViewModel){
    val state = viewModel.state.value



    Scaffold {  paddingValues: PaddingValues ->
        Column(modifier = Modifier.padding(paddingValues))
        {
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.message.isNotEmpty()){
                Text(state.message)
            }
            state.jokes?.let { jokes ->
                LazyColumn {
                    items(jokes){ joke ->
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
    }
}