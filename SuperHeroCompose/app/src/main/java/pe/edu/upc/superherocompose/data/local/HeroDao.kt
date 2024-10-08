package pe.edu.upc.superherocompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HeroDao {
    @Insert
    suspend fun insert(heroEntity: HeroEntity)

    @Insert
    suspend fun delete(heroEntity: HeroEntity)

    @Query("select * from heroes where id =:id")
    suspend fun fetchHeroById(id: String): HeroEntity?
}