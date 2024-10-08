package pe.edu.upc.jokescompose.presentation.joke

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.jokescompose.common.Resource
import pe.edu.upc.jokescompose.data.repository.JokeRepository
import pe.edu.upc.jokescompose.domain.Joke

class JokeViewModel(private val repository: JokeRepository) : ViewModel() {
    private val _state = mutableStateOf(JokeState())
    val state: State<JokeState> get() = _state

    fun onScoreChanged(score: Int) {
        _state.value.joke?.let { joke: Joke ->
            _state.value =
                JokeState(
                    joke = Joke(
                        joke.description,
                        if (score == joke.score) 0 else score
                    )
                )
            val previousScore = joke.score

            viewModelScope.launch {
                if (previousScore == 0) {
                    repository.insertJoke(Joke(joke.description, score))
                    return@launch
                }
                if (score == previousScore) {
                    repository.deleteJoke(joke)
                    return@launch
                }
                if (previousScore > 0) {
                    repository.updateJoke(Joke(joke.description, score))
                    return@launch
                }

            }

        }
    }


    fun getJoke() {
        _state.value = JokeState(isLoading = true)
        viewModelScope.launch {
            val result = repository.getJoke()
            if (result is Resource.Success) {
                _state.value = JokeState(joke = result.data)
            } else {
                _state.value = JokeState(message = result.message ?: "An error occurred")
            }

        }
    }
}


data class JokeState(
    val isLoading: Boolean = false,
    val joke: Joke? = null,
    val message: String = ""
)