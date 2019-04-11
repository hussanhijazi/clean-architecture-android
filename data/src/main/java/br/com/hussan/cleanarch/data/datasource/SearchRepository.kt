package br.com.hussan.cleanarch.data.datasource

import br.com.hussan.cleanarch.data.cache.SearchCache
import br.com.hussan.cleanarch.domain.Search
import io.reactivex.Completable
import io.reactivex.Single

class SearchRepository(private val cache: SearchCache) : SearchDatasource {
    override fun getSearches(): Single<List<Search>> = cache.getSearches()
    override fun saveSearch(search: Search): Completable {
        return cache.saveSearch(search)
    }

}

interface SearchDatasource {
    fun getSearches(): Single<List<Search>>
    fun saveSearch(search: Search): Completable
}
