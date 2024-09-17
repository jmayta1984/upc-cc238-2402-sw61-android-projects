package pe.edu.upc.superherocompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.superherocompose.common.Resource
import pe.edu.upc.superherocompose.data.local.HeroDao
import pe.edu.upc.superherocompose.data.local.HeroEntity
import pe.edu.upc.superherocompose.data.remote.HeroDto
import pe.edu.upc.superherocompose.data.remote.HeroService
import pe.edu.upc.superherocompose.data.remote.toHero
import pe.edu.upc.superherocompose.domain.Hero

class HeroRepository(
    private val heroService: HeroService,
    private val heroDao: HeroDao
) {

    private suspend fun isFavorite(id: String): Boolean = withContext(Dispatchers.IO) {
        heroDao.fetchHeroById(id)?.let {
            return@withContext true
        }
        return@withContext false
    }

    suspend fun searchHero(name: String): Resource<List<Hero>> = withContext(Dispatchers.IO) {
        val response = heroService.searchHero(name)

        if (response.isSuccessful) {
            response.body()?.heroes?.let { heroesDto ->
                val heroes = mutableListOf<Hero>()
                heroesDto.forEach { heroDto: HeroDto ->
                    val hero = heroDto.toHero()
                    hero.isFavorite = isFavorite(heroDto.id)
                    heroes += hero
                }
                return@withContext Resource.Success(data = heroes.toList())
            }
            return@withContext Resource.Error(message = response.body()?.error ?: "")
        } else {
            return@withContext Resource.Error(message = "Data not found")
        }
    }

    suspend fun insertHero(id: String, name: String, fullName: String, url: String) =
        withContext(Dispatchers.IO) {
            heroDao.insert(HeroEntity(id, name, fullName, url))
        }

    suspend fun deleteHero(id: String, name: String, fullName: String, url: String) =
        withContext(Dispatchers.IO) {
            heroDao.delete(HeroEntity(id, name, fullName, url))
        }
}