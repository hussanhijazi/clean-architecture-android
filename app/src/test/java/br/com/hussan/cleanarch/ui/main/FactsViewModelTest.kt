package br.com.hussan.cleanarch.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.hussan.cleanarch.domain.Fact
import br.com.hussan.cleanarch.domain.Search
import br.com.hussan.cleanarch.usecases.GetFacts
import br.com.hussan.cleanarch.usecases.SaveCategories
import br.com.hussan.cleanarch.usecases.SaveSearch
import io.reactivex.Completable
import io.reactivex.Observable
import mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class FactsViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getFactsCase: GetFacts = mock()
    private val saveCategoryCase: SaveCategories = mock()
    private var saveSearch: SaveSearch = mock()
    private lateinit var mViewModel: FactsViewModel

    @Before
    fun setUp() {
        mViewModel = FactsViewModel(getFactsCase, saveCategoryCase, saveSearch)
    }

    @Test
    fun `Get facts using query`() {

        val query = "car"

        val facts = listOf(Fact("Fact", ""))
        val search = Search(query)
        `when`(getFactsCase.invoke(query)).thenReturn(Observable.fromArray(facts))
        `when`(saveSearch.invoke(search)).thenReturn(Completable.complete())

        mViewModel.getFacts(query)
            .test()
            .assertValue(facts)
            .assertComplete()

        verify(getFactsCase).invoke(query)

    }

    @Test
    fun `Get facts using query with error`() {

        val exception = Exception()
        val query = "car"

        `when`(getFactsCase.invoke(query)).thenReturn(Observable.error(exception))

        mViewModel.getFacts(query)
            .test()
            .assertError(exception)

    }

    @Test
    fun `Get_save categories`() {

        `when`(saveCategoryCase.invoke()).thenReturn(Completable.complete())

        mViewModel.getCategories()
            .test()
            .assertNoValues()
            .assertComplete()

        verify(saveCategoryCase).invoke()

    }
}
