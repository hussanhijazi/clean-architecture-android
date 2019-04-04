package br.com.hussan.cleanarch.data.datasource

import br.com.hussan.cleanarch.data.AppApi
import br.com.hussan.cleanarch.data.RetryWithDelay
import br.com.hussan.cleanarch.data.cache.FactCache
import br.com.hussan.cleanarch.domain.Fact
import io.reactivex.Observable
import io.reactivex.Single

class FactRepository(
    private val api: AppApi, private val factCache: FactCache
) : FactDatasource {

    companion object {
        const val FIRST_CALL = 4
        const val SECOND_CALL = 8
    }

    override fun getFacts(query: String): Observable<List<Fact>> {

        return api.getFacts(query).map { it.result }
            .retryWhen(RetryWithDelay(listOf(FIRST_CALL, SECOND_CALL)))
            .flatMap {
                val factsWithQuery = it.map {
                    it.query = query
                    it
                }
                factCache.saveFacts(factsWithQuery).andThen(Observable.just(it))
            }
    }

    override fun getRandomFacts(): Single<List<Fact>> {
        return factCache.getRandomFacts()
    }
}

interface FactDatasource {
    fun getFacts(query: String): Observable<List<Fact>>
    fun getRandomFacts(): Single<List<Fact>>
}
