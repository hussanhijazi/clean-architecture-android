package br.com.hussan.cleanarch.usecases

import br.com.hussan.cleanarch.data.datasource.SearchRepository
import br.com.hussan.cleanarch.domain.Search
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class SaveSearchTest {

    private lateinit var saveSearch: SaveSearch
    private lateinit var repository: SearchRepository

    @Before
    fun setUp() {
        repository = mock()
        saveSearch = SaveSearch(repository)
    }

    @Test
    fun `Save search call repository`() {
        val search = Search("search")
        `when`(repository.saveSearch(search)).thenReturn(Completable.complete())

        saveSearch.invoke(search).test()
            .assertNoErrors()
            .assertComplete()


        verify(repository).saveSearch(search)
        verifyNoMoreInteractions(repository)

    }

    @Test
    fun `Save search call repository with error`() {
        val exception = Exception()
        val search = Search("search")

        `when`(repository.saveSearch(search)).thenReturn(Completable.error(exception))

        saveSearch.invoke(search).test().assertError(exception)


        verify(repository).saveSearch(search)
        verifyNoMoreInteractions(repository)

    }
}
