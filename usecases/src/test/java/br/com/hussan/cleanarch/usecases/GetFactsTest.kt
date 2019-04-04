package br.com.hussan.cleanarch.usecases

import br.com.hussan.cleanarch.data.datasource.FactDatasource
import br.com.hussan.cleanarch.domain.Fact
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class GetFactsTest {

    private lateinit var getFacts: GetFacts
    private lateinit var repository: FactDatasource

    @Before
    fun setUp() {
        repository = mock()
        getFacts = GetFacts(repository)
    }

    @Test
    fun `Get facts with api`() {

        val query = "query"
        val facts = listOf(Fact("", "Fact"))
        `when`(repository.getFacts(query)).thenReturn(Observable.just(facts))

        getFacts.invoke(query).test()
            .assertValue(facts)
            .assertNoErrors()
            .assertComplete()


        verify(repository).getFacts(query)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Get facts with api with error`() {

        val query = "query"
        val exception = Exception()
        `when`(repository.getFacts(query)).thenReturn(Observable.error(exception))

        getFacts.invoke(query).test()
            .assertError(exception)

        verify(repository).getFacts(query)
        verifyNoMoreInteractions(repository)
    }

}
