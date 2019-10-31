package br.com.hussan.cleanarch.usecases.di

import br.com.hussan.cleanarch.usecases.*
import org.koin.dsl.module

val useCaseModule = module {
    single { GetFacts(get()) }
    single { SaveCategories(get()) }
    single { GetCategories(get()) }
    single { GetSearches(get()) }
    single { SaveSearch(get()) }
}
