package br.com.hussan.cleanarch.data.dataSource

import br.com.hussan.cleanarch.data.cache.SearchCache
import br.com.hussan.cleanarch.data.datasource.SearchDatasource
import br.com.hussan.cleanarch.data.datasource.SearchRepository
import br.com.hussan.cleanarch.data.mock
import br.com.hussan.cleanarch.domain.Search
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class SearchRepositoryTest {

    lateinit var repository: SearchDatasource
    lateinit var cache: SearchCache


    @Before
    @Throws
    fun setUp() {

        cache = mock()
        repository = SearchRepository(cache)

    }

    @Test
    fun `Get searches locally`() {

        val searches = listOf(Search("search"))

        `when`(cache.getSearches()).thenReturn(Single.just(searches))

        repository.getSearches().test().apply {
            assertValue(searches)
        }

        verify(cache).getSearches()
    }

    @Test
    fun `Get searches locally with error`() {

        val exception = Exception()

        `when`(cache.getSearches()).thenReturn(Single.error(exception))

        repository.getSearches().test()
            .assertError(exception)


        verify(cache).getSearches()
    }

    @Test
    fun `Save search locally`() {
        val search = Search("search")

        `when`(cache.saveSearch(search)).thenReturn(Completable.complete())

        val testObserver = repository.saveSearch(search).test()

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        verify(cache).saveSearch(search)
    }

    @Test
    fun `Save search locally with error`() {

        val search = Search("search")
        val exception = Exception()

        `when`(cache.saveSearch(search)).thenReturn(Completable.error(exception))

        repository.saveSearch(search).test().assertError(exception)

        verify(cache).saveSearch(search)
    }
}
