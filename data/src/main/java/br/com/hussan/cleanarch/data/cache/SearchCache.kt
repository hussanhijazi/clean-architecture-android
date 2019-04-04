package br.com.hussan.cleanarch.data.cache

import br.com.hussan.cleanarch.domain.Search
import io.reactivex.Completable
import io.reactivex.Flowable

interface SearchCache {
    fun saveSearch(search: Search): Completable
    fun isCached(): Boolean
    fun getSearches(): Flowable<List<Search>>
}
