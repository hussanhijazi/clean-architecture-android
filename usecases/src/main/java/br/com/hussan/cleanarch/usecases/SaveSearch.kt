package br.com.hussan.cleanarch.usecases

import br.com.hussan.cleanarch.data.datasource.SearchDatasource
import br.com.hussan.cleanarch.domain.Search
import io.reactivex.Completable

class SaveSearch(private val dataSource: SearchDatasource) {
    operator fun invoke(search: Search): Completable {
        return dataSource.saveSearch(search)
    }
}

