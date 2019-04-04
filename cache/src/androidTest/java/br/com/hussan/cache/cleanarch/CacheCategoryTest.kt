package br.com.hussan.cleanarch

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.hussan.cache.cleanarch.AppDatabase
import br.com.hussan.cache.cleanarch.CategoryCacheImpl
import br.com.hussan.cache.cleanarch.mapper.CategoryEntityMapper
import br.com.hussan.cleanarch.data.cache.CategoryCache
import br.com.hussan.cleanarch.domain.Category
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CacheCategoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase

    private var entityMapper = CategoryEntityMapper()

    private lateinit var categoryCache: CategoryCache

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        categoryCache = CategoryCacheImpl(appDatabase, entityMapper)
    }

    @Test
    fun saveCategories() {

        val categories = listOf(Category(1, "dev"))

        categoryCache.saveCategories(categories).test()
            .assertNoValues()
            .assertComplete()

    }

    @Test
    fun saveCategoriesAndGet() {

        val categories = listOf(Category(1, "dev"))

        categoryCache.saveCategories(categories).test()
            .assertNoValues()
            .assertComplete()

        categoryCache.getCategories().test()
            .assertValue(categories)
    }

}
