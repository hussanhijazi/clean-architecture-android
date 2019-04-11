package br.com.hussan.cache.cleanarch.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.hussan.cache.cleanarch.model.SearchEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface SearchDao {
    @Insert(onConflict = REPLACE)
    fun insert(search: SearchEntity): Completable

    @Query("SELECT * from search order by searchedAt desc")
    fun loadSearches(): Single<List<SearchEntity>>
}
