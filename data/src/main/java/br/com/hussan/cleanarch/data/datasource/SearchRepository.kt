package br.com.hussan.cleanarch.data.datasource

import br.com.hussan.cleanarch.data.cache.SearchCache
import br.com.hussan.cleanarch.domain.Search
import io.reactivex.Completable
import io.reactivex.Flowable

class SearchRepository(private val cache: SearchCache) : SearchDatasource {
    override fun getSearches(): Flowable<List<Search>> = cache.getSearches()
    override fun saveSearch(search: Search): Completable {
        return cache.saveSearch(search)
    }

}

interface SearchDatasource {
    fun getSearches(): Flowable<List<Search>>
    fun saveSearch(search: Search): Completable
}
