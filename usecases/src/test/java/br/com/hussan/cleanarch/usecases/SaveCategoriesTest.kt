package br.com.hussan.cleanarch.usecases

import br.com.hussan.cleanarch.data.datasource.CategoryDatasource
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class SaveCategoriesTest {

    private lateinit var saveCategories: SaveCategories
    private lateinit var repository: CategoryDatasource

    @Before
    fun setUp() {
        repository = mock()
        saveCategories = SaveCategories(repository)
    }

    @Test
    fun `Save categories call repository`() {
        `when`(repository.saveCategories()).thenReturn(Completable.complete())

        saveCategories.invoke().test()
            .assertNoErrors()
            .assertComplete()

        verify(repository).saveCategories()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Save categories call repository with error`() {
        val exception = Exception()
        `when`(repository.saveCategories()).thenReturn(Completable.error(exception))

        saveCategories.invoke().test().assertError(exception)

        verify(repository).saveCategories()
        verifyNoMoreInteractions(repository)

    }
}
