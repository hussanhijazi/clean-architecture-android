package br.com.hussan.cache.cleanarch

import br.com.hussan.cache.cleanarch.mapper.FactEntityMapper
import br.com.hussan.cleanarch.data.cache.FactCache
import br.com.hussan.cleanarch.domain.Fact
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class FactCacheImpl(
    private val db: AppDatabase,
    private val mapper: FactEntityMapper
) :
    FactCache {
    override fun getRandomFacts(): Single<List<Fact>> {
        return db.factDao().loadRandomFacts().map { it.map { mapper.mapFromCached(it) } }
    }

    override fun getFacts(query: String): Flowable<List<Fact>> {
        return db.factDao().loadFacts(query).map { it.map { mapper.mapFromCached(it) } }
    }

    override fun saveFacts(facts: List<Fact>): Completable {
        return db.factDao().insertAll(facts.map {
            mapper.mapToCached(it)
        })
    }

}
