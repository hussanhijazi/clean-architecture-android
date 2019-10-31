package br.com.hussan.cleanarch

import android.app.Application
import br.com.hussan.cache.cleanarch.di.cacheModule
import br.com.hussan.cleanarch.data.di.apiModule
import br.com.hussan.cleanarch.data.di.dataModule
import br.com.hussan.cleanarch.di.appModule
import br.com.hussan.cleanarch.usecases.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    appModule,
                    useCaseModule,
                    apiModule,
                    dataModule,
                    cacheModule
                )
            ).androidContext(this@MyApp)
        }

    }
}
