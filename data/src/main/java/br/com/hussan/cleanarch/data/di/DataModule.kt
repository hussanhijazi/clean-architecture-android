package br.com.hussan.cleanarch.data.di

import br.com.hussan.cleanarch.data.datasource.*
import org.koin.dsl.module.module

val dataModule = module {
    single<FactDatasource> { FactRepository(get(), get()) }
    single<CategoryDatasource> { CategoryRepository(get(), get()) }
    single<SearchDatasource> { SearchRepository(get()) }
}
