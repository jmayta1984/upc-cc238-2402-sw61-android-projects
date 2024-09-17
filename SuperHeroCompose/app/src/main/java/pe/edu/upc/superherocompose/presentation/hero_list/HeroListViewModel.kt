package pe.edu.upc.superherocompose.presentation.hero_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.superherocompose.common.Resource
import pe.edu.upc.superherocompose.data.repository.HeroRepository
import pe.edu.upc.superherocompose.domain.Hero

class HeroListViewModel(private val heroRepository: HeroRepository) : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> get() = _name

    private val _state = mutableStateOf(HeroListState())
    val state: State<HeroListState> get() = _state

    fun onNameChanged(name: String) {
        _name.value = name
    }

    fun onToggleFavorite(hero: Hero) {
        hero.isFavorite = !hero.isFavorite
        viewModelScope.launch {
            if (hero.isFavorite) {
                heroRepository.insertHero(hero.id, hero.name, hero.fullName, hero.url)
            } else {
                heroRepository.deleteHero(hero.id, hero.name, hero.fullName, hero.url)
            }

            val heroes = _state.value.heroes ?: emptyList()
            _state.value = HeroListState(heroes = emptyList())
            _state.value = HeroListState(heroes = heroes)


        }

    }


    fun searchHero() {
        _state.value = HeroListState(isLoading = true)
        viewModelScope.launch {
            val result = heroRepository.searchHero(_name.value)

            if (result is Resource.Success) {
                _state.value = HeroListState(heroes = result.data)
            } else {
                _state.value = HeroListState(error = result.message ?: "An error occurred")
            }

        }
    }

}