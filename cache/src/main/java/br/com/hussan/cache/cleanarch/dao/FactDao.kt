package br.com.hussan.cache.cleanarch.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.hussan.cache.cleanarch.model.FactEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(facts: List<FactEntity>): Completable

    @Query("SELECT * from fact WHERE term = :term")
    fun loadFacts(term: String): Flowable<List<FactEntity>>

    @Query("SELECT * from fact order by RANDOM() LIMIT 10")
    fun loadRandomFacts(): Single<List<FactEntity>>

}
