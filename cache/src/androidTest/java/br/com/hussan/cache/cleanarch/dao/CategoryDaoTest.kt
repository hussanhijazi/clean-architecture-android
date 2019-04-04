package br.com.hussan.cleanarch

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.hussan.cache.cleanarch.AppDatabase
import br.com.hussan.cache.cleanarch.dao.CategoryDao
import br.com.hussan.cache.cleanarch.model.CategoryEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var categoryDao: CategoryDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        categoryDao = db.categoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveCategories() {

        val categories = listOf(CategoryEntity(1, "category"))

        categoryDao.insertAll(categories).test()
            .assertComplete()
            .assertNoValues()

    }

    @Test
    fun loadCategories() {

        val categories = listOf(CategoryEntity(1, "category"))

        categoryDao.insertAll(categories).test()
            .assertNoValues()
            .assertComplete()

        categoryDao.loadCategories().test()
            .assertValue(categories)
    }
}

