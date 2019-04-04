package br.com.hussan.cleanarch.data.dataSource

import br.com.hussan.cleanarch.data.AppApi
import br.com.hussan.cleanarch.data.FactsResponse
import br.com.hussan.cleanarch.data.cache.FactCache
import br.com.hussan.cleanarch.data.datasource.FactDatasource
import br.com.hussan.cleanarch.data.datasource.FactRepository
import br.com.hussan.cleanarch.data.mock
import br.com.hussan.cleanarch.domain.Fact
import com.google.gson.Gson
import io.reactivex.Completable
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class FactRepositoryTest {

    lateinit var repository: FactDatasource
    lateinit var api: AppApi
    lateinit var mockServer: MockWebServer
    lateinit var gson: Gson
    lateinit var factCache: FactCache


    @Before
    @Throws
    fun setUp() {

        gson = Gson()
        factCache = mock()

        mockServer = MockWebServer()
        val baseURL = mockServer.url("/").toString()

        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        api = retrofit.create(AppApi::class.java)

        repository = FactRepository(api, factCache)

    }

    @Test
    fun `Get facts remote and save locally`() {

        val query = "query"
        val factResponse = FactsResponse(10, listOf(Fact("id", "Chuck Norris", query = "")))
        val path = "/jokes/search?query=$query"

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(gson.toJson(factResponse))
        mockServer.enqueue(mockResponse)

        val factsToSave = factResponse.result.map {
            it.query = query
            it
        }

        `when`(factCache.saveFacts(factsToSave)).thenReturn(Completable.complete())

        repository.getFacts(query).test().apply {
            awaitTerminalEvent(1, TimeUnit.SECONDS)
            assertValue(factResponse.result)
            assertNoErrors()
            assertValueCount(1)
        }

        val request = mockServer.takeRequest()
        assertEquals(path, request.path)

        verify(factCache).saveFacts(factsToSave)

    }

}
