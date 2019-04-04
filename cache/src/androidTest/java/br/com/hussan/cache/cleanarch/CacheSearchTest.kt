package br.com.hussan.cleanarch

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.hussan.cache.cleanarch.AppDatabase
import br.com.hussan.cache.cleanarch.SearchCacheImpl
import br.com.hussan.cache.cleanarch.mapper.SearchEntityMapper
import br.com.hussan.cleanarch.data.cache.SearchCache
import br.com.hussan.cleanarch.domain.Search
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CacheSearchTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase

    private var entityMapper = SearchEntityMapper()

    private lateinit var searchCache: SearchCache

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        searchCache = SearchCacheImpl(appDatabase, entityMapper)
    }

    @Test
    fun saveSearch() {

        val search = Search("111")

        searchCache.saveSearch(search).test()
            .assertNoValues()
            .assertComplete()

    }

    @Test
    fun saveSearchAndGet() {

        val search = Search("111")

        searchCache.saveSearch(search).test()
            .assertNoValues()
            .assertComplete()

        searchCache.getSearches().test()
            .assertValue(listOf(search))
    }

}
