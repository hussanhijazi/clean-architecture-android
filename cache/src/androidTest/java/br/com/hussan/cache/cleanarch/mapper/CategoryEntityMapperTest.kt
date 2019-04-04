package br.com.hussan.cache.cleanarch.mapper

import br.com.hussan.cache.cleanarch.model.CategoryEntity
import br.com.hussan.cleanarch.domain.Category
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CategoryEntityMapperTest {

    private lateinit var categoryEntityMapper: CategoryEntityMapper

    @Before
    fun setUp() {
        categoryEntityMapper = CategoryEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val category = Category(1, "cat")
        val cachedCategory = categoryEntityMapper.mapToCached(category)

        assertDataEquality(category, cachedCategory)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedCategory = CategoryEntity(1, "cat")
        val category = categoryEntityMapper.mapFromCached(cachedCategory)

        assertDataEquality(category, cachedCategory)
    }

    private fun assertDataEquality(
        category: Category,
        cachedCategory: CategoryEntity
    ) {
        assertEquals(category.name, cachedCategory.name)
    }

}
