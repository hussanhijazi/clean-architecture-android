package br.com.hussan.cleanarch.data.cache

import br.com.hussan.cleanarch.domain.Fact
import io.reactivex.Completable
import io.reactivex.Single

interface FactCache {
    fun saveFacts(facts: List<Fact>): Completable
    fun getFacts(query: String): Single<List<Fact>>
    fun getRandomFacts(): Single<List<Fact>>
}
