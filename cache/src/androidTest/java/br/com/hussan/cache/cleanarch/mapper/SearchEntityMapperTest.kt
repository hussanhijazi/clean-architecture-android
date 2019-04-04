package br.com.hussan.cache.cleanarch.mapper

import br.com.hussan.cache.cleanarch.model.SearchEntity
import br.com.hussan.cleanarch.domain.Search
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Date

@RunWith(JUnit4::class)
class SearchEntityMapperTest {

    private lateinit var searchEntityMapper: SearchEntityMapper

    @Before
    fun setUp() {
        searchEntityMapper = SearchEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val search = Search("car")
        val cachedSearch = searchEntityMapper.mapToCached(search)

        assertDataEquality(search, cachedSearch)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedSearch = SearchEntity("car", Date())
        val search = searchEntityMapper.mapFromCached(cachedSearch)

        assertDataEquality(search, cachedSearch)
    }

    private fun assertDataEquality(
        search: Search,
        cachedSearch: SearchEntity
    ) {
        assertEquals(search.query, cachedSearch.query)
    }
}
