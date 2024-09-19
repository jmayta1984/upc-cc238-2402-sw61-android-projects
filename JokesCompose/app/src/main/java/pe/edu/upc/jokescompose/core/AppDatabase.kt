package pe.edu.upc.jokescompose.core

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.jokescompose.data.local.JokeDao
import pe.edu.upc.jokescompose.data.local.JokeEntity

@Database(entities = [JokeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getJokeDao(): JokeDao
}