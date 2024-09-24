package pe.edu.upc.jokescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import pe.edu.upc.jokescompose.common.Constants
import pe.edu.upc.jokescompose.core.AppDatabase
import pe.edu.upc.jokescompose.data.repository.JokeRepository
import pe.edu.upc.jokescompose.data.remote.JokeService
import pe.edu.upc.jokescompose.presentation.joke.JokeScreen
import pe.edu.upc.jokescompose.presentation.joke.JokeViewModel
import pe.edu.upc.jokescompose.presentation.jokelist.JokeListScreen
import pe.edu.upc.jokescompose.presentation.jokelist.JokeListViewModel
import pe.edu.upc.jokescompose.ui.theme.JokesComposeTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val service = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JokeService::class.java)

        val dao = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "jokes-db")
            .build()
            .getJokeDao()

        val repository = JokeRepository(service, dao)

        super.onCreate(savedInstanceState)
        setContent {
            val index = remember {
                mutableStateOf(0)
            }
            JokesComposeTheme {
                Column {

                    Column (modifier = Modifier.weight(1f)){
                        if (index.value == 0) {
                            val jokeViewModel = JokeViewModel(repository)
                            JokeScreen(jokeViewModel)
                        } else {
                            val jokeListViewModel = JokeListViewModel(repository)
                            JokeListScreen(jokeListViewModel)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
                    ) {
                        FilterChip(
                            selected = (index.value == 0),
                            label = { Text("Search") },
                            onClick = {
                                index.value = 0
                            })
                        FilterChip(
                            selected = (index.value == 1),
                            label = { Text("Favorites") },
                            onClick = {
                                index.value = 1
                            })
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JokesComposeTheme {
        Greeting("Android")
    }
}