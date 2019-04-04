package br.com.hussan.cleanarch.usecases

import br.com.hussan.cleanarch.data.datasource.CategoryDatasource
import br.com.hussan.cleanarch.domain.Category
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class GetCategoriesTest {

    private lateinit var getCategories: GetCategories
    private lateinit var repository: CategoryDatasource

    @Before
    fun setUp() {
        repository = mock()
        getCategories = GetCategories(repository)
    }

    @Test
    fun `Get categories call locally`() {
        val categories = listOf(Category(1, "Chuck Norris"))

        `when`(repository.getCategories()).thenReturn(Flowable.just(categories))

        getCategories.invoke().test()
            .assertValue(categories)
            .assertNoErrors()
            .assertComplete()


        verify(repository).getCategories()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Get categories call locally with error`() {
        val exception = Exception()
        `when`(repository.getCategories()).thenReturn(Flowable.error(exception))

        getCategories.invoke().test().assertError(exception)

        verify(repository).getCategories()
        verifyNoMoreInteractions(repository)
    }
}
