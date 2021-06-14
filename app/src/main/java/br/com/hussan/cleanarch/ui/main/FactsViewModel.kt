package br.com.hussan.cleanarch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.hussan.cleanarch.data.mapper.FactViewMapper
import br.com.hussan.cleanarch.data.model.FactViewModel
import br.com.hussan.cleanarch.domain.Search
import br.com.hussan.cleanarch.usecases.GetFacts
import br.com.hussan.cleanarch.usecases.SaveCategories
import br.com.hussan.cleanarch.usecases.SaveSearch
import io.reactivex.Observable
import io.reactivex.Single

class FactsViewModel(
    private val getFactsCase: GetFacts,
    private val saveCategoriesCase: SaveCategories,
    private val saveSearchCase: SaveSearch,
    private val mapper: FactViewMapper
) : ViewModel() {

    private val results = MutableLiveData<List<FactViewModel>>()

    val resultsFacts: LiveData<List<FactViewModel>>
        get() = results

    fun getFacts(query: String): Observable<List<FactViewModel>> = getFactsCase.invoke(query)
        .map { it.map(mapper::mapToView) }
        .doOnNext { results.postValue(it) }
        .flatMap { data ->
            if (data.isNotEmpty())
                saveSearchCase(Search(query)).andThen(Observable.just(data))
            else
                Observable.just(data)
        }

    fun getRandomFacts(): Single<List<FactViewModel>> {
        return getFactsCase.getRandomFacts().map {
            val result = it.map(mapper::mapToView)
            results.postValue(result)
            result
        }

    }

    fun getCategories() = saveCategoriesCase.invoke()

}

