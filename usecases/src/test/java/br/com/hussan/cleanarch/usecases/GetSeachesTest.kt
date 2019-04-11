package br.com.hussan.cleanarch.usecases

import br.com.hussan.cleanarch.data.datasource.SearchDatasource
import br.com.hussan.cleanarch.domain.Search
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class GetSeachesTest {

    private lateinit var getSearches: GetSearches
    private lateinit var repository: SearchDatasource

    @Before
    fun setUp() {
        repository = mock()
        getSearches = GetSearches(repository)
    }

    @Test
    fun `Get search call locally`() {
        val searches = listOf(Search("car"))

        `when`(repository.getSearches()).thenReturn(Single.just(searches))

        getSearches.invoke().test()
            .assertValue(searches)
            .assertNoErrors()
            .assertComplete()


        verify(repository).getSearches()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Get search call locally with error`() {
        val exception = Exception()
        `when`(repository.getSearches()).thenReturn(Single.error(exception))

        getSearches.invoke().test().assertError(exception)

        verify(repository).getSearches()
        verifyNoMoreInteractions(repository)
    }
}
