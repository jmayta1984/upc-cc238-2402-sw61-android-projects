package pe.edu.upc.superherocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import pe.edu.upc.superherocompose.common.Constants
import pe.edu.upc.superherocompose.data.local.AppDatabase
import pe.edu.upc.superherocompose.data.remote.HeroService
import pe.edu.upc.superherocompose.data.repository.HeroRepository
import pe.edu.upc.superherocompose.presentation.hero_list.HeroListScreen
import pe.edu.upc.superherocompose.presentation.hero_list.HeroListViewModel
import pe.edu.upc.superherocompose.ui.theme.SuperHeroComposeTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val service = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeroService::class.java)

        val dao = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db-heroes"
        )
            .build()
            .getHeroDao()

        val viewModel = HeroListViewModel(HeroRepository(service, dao))

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperHeroComposeTheme {
                HeroListScreen(viewModel)
            }
        }
    }
}

