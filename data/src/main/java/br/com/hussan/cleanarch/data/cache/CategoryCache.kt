package br.com.hussan.cleanarch.data.cache

import br.com.hussan.cleanarch.domain.Category
import io.reactivex.Completable
import io.reactivex.Flowable

interface CategoryCache {
    fun saveCategories(categories: List<Category>): Completable
    fun isCached(): Boolean
    fun getCategories(): Flowable<List<Category>>
}
