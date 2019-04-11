package br.com.hussan.cleanarch.data.dataSource

import br.com.hussan.cleanarch.data.AppApi
import br.com.hussan.cleanarch.data.cache.CategoryCache
import br.com.hussan.cleanarch.data.datasource.CategoryDatasource
import br.com.hussan.cleanarch.data.datasource.CategoryRepository
import br.com.hussan.cleanarch.data.mock
import br.com.hussan.cleanarch.domain.Category
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import junit.framework.TestCase
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
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
class CategoryRepositoryTest {

    lateinit var repository: CategoryDatasource
    lateinit var api: AppApi
    lateinit var cache: CategoryCache
    lateinit var mockServer: MockWebServer
    lateinit var gson: Gson


    @Before
    @Throws
    fun setUp() {

        cache = mock()
        gson = Gson()

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

        repository = CategoryRepository(api, cache)

    }

    @Test
    fun `Get Categories locally`() {

        val categories = listOf(Category(1, "Category"))

        `when`(cache.getCategories()).thenReturn(Single.just(categories))

        repository.getCategories()

        verify(cache).getCategories()
    }

    @Test
    fun `Get Categories remote and Save`() {
        val categoriesApi = listOf("Chuck Norris")
        val categories = listOf(Category(1, "Chuck Norris"))
        val path = "/jokes/categories"

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(gson.toJson(categoriesApi))
        mockServer.enqueue(mockResponse)


        `when`(cache.isCached()).thenReturn(false)
        `when`(cache.saveCategories(categories)).thenReturn(Completable.complete())

        repository.saveCategories().test().apply {
            awaitTerminalEvent(1, TimeUnit.SECONDS)
            assertNoErrors()
            assertComplete()
        }

        verify(cache).isCached()
        verify(cache).saveCategories(categories)

        val request = mockServer.takeRequest()
        TestCase.assertEquals(path, request.path)

    }


    @Test
    fun `Check if categories cached`() {

        `when`(cache.isCached()).thenReturn(true)

        repository.saveCategories().test().apply {
            assertComplete()
        }

        verify(cache).isCached()
    }
}
