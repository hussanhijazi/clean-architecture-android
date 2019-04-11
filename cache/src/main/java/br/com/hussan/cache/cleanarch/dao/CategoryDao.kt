package br.com.hussan.cache.cleanarch.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.hussan.cache.cleanarch.model.CategoryEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<CategoryEntity>): Completable

    @Query("SELECT * from category order by RANDOM() LIMIT 8")
    fun loadCategories(): Single<List<CategoryEntity>>

}
