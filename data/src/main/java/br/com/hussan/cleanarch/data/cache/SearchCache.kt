package br.com.hussan.cleanarch.data.cache

import br.com.hussan.cleanarch.domain.Search
import io.reactivex.Completable
import io.reactivex.Single

interface SearchCache {
    fun saveSearch(search: Search): Completable
    fun isCached(): Boolean
    fun getSearches(): Single<List<Search>>
}
