package br.com.hussan.cleanarch

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.hussan.cache.cleanarch.AppDatabase
import br.com.hussan.cache.cleanarch.dao.SearchDao
import br.com.hussan.cache.cleanarch.model.SearchEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.Date

@RunWith(AndroidJUnit4::class)
class SearchDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var searchDao: SearchDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        searchDao = db.searchDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveSearch() {

        val search = SearchEntity("search", Date())

        searchDao.insert(search).test()
            .assertComplete()
            .assertNoValues()

    }

    @Test
    fun saveSearchAndGet() {

        val search = SearchEntity("search", Date())

        searchDao.insert(search).test()
            .assertComplete()
            .assertNoValues()

        searchDao.loadSearches().test()
            .assertValue(listOf(search))
    }
}

