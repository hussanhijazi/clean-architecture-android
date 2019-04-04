package br.com.hussan.cache.cleanarch.di

import androidx.room.Room.databaseBuilder
import br.com.hussan.cache.cleanarch.AppDatabase
import br.com.hussan.cache.cleanarch.CategoryCacheImpl
import br.com.hussan.cache.cleanarch.FactCacheImpl
import br.com.hussan.cache.cleanarch.SearchCacheImpl
import br.com.hussan.cache.cleanarch.mapper.CategoryEntityMapper
import br.com.hussan.cache.cleanarch.mapper.FactEntityMapper
import br.com.hussan.cache.cleanarch.mapper.SearchEntityMapper
import br.com.hussan.cleanarch.data.cache.CategoryCache
import br.com.hussan.cleanarch.data.cache.FactCache
import br.com.hussan.cleanarch.data.cache.SearchCache
import org.koin.dsl.module.module

val cacheModule = module {
    single {
        databaseBuilder(
            get(),
            AppDatabase::class.java, "chucknorris-challenge"
        ).build()
    }
    single { CategoryEntityMapper() }

    single { SearchEntityMapper() }

    single { FactEntityMapper() }


    single<CategoryCache> {
        CategoryCacheImpl(get(), get())
    }

    single<SearchCache> {
        SearchCacheImpl(get(), get())
    }

    single<FactCache> {
        FactCacheImpl(get(), get())
    }
}
