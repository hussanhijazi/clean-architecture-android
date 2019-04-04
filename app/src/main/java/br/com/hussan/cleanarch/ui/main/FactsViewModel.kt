package br.com.hussan.cleanarch.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.hussan.cleanarch.domain.Fact
import br.com.hussan.cleanarch.domain.Search
import br.com.hussan.cleanarch.usecases.GetFacts
import br.com.hussan.cleanarch.usecases.SaveCategories
import br.com.hussan.cleanarch.usecases.SaveSearch
import io.reactivex.Observable
import io.reactivex.Single

class FactsViewModel(
    private val getFactsCase: GetFacts,
    private val saveCategoriesCase: SaveCategories,
    private val saveSearchCase: SaveSearch
) : ViewModel() {

    private val results = MutableLiveData<List<Fact>>()

    fun getFacts(query: String) = getFactsCase.invoke(query)
        .doOnNext { results.postValue(it) }
        .flatMap { data ->
            if (data.isNotEmpty())
                saveSearchCase(Search(query)).andThen(Observable.just(data))
            else
                Observable.just(data)
        }

    fun getRandomFacts(): Single<List<Fact>> {
        return getFactsCase.getRandomFacts()
    }

    fun getCategories() = saveCategoriesCase.invoke()

    fun getResultFacts() = results
}

