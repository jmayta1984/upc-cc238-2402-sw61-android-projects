package pe.edu.upc.superherocompose.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.superherocompose.domain.Hero

data class HeroesResponseDto(
    val response: String,
    @SerializedName("results")
    val heroes: List<HeroDto>?,
    val error: String?
)

data class HeroDto(
    val id: String,
    val name: String,
    val biography: Biography,
    val image: Poster,
)

data class Biography(
    @SerializedName("full-name")
    val fullName: String
)

data class Poster(
    val url: String
)

fun HeroDto.toHero(): Hero {
    return Hero(name, biography.fullName, image.url)
}