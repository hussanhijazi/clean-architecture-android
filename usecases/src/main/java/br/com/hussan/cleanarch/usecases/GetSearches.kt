package br.com.hussan.cleanarch.usecases

import br.com.hussan.cleanarch.data.datasource.SearchDatasource
import br.com.hussan.cleanarch.domain.Search
import io.reactivex.Flowable

class GetSearches(private val dataSource: SearchDatasource) {
    operator fun invoke(): Flowable<List<Search>> {
        return dataSource.getSearches()
    }
}
