package br.com.hussan.cache.cleanarch

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.hussan.cache.cleanarch.dao.CategoryDao
import br.com.hussan.cache.cleanarch.dao.FactDao
import br.com.hussan.cache.cleanarch.dao.SearchDao
import br.com.hussan.cache.cleanarch.model.CategoryEntity
import br.com.hussan.cache.cleanarch.model.FactEntity
import br.com.hussan.cache.cleanarch.model.SearchEntity

@Database(entities = [CategoryEntity::class, SearchEntity::class, FactEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun searchDao(): SearchDao
    abstract fun factDao(): FactDao

}
