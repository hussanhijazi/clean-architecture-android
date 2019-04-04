package br.com.hussan.cleanarch.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {
    @GET("jokes/search")
    fun getFacts(@Query("query") query: String): Observable<FactsResponse>

    @GET("jokes/categories")
    fun getCategories(): Observable<List<String>>
}
