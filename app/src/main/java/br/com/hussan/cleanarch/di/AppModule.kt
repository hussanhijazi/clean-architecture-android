package br.com.hussan.cleanarch.di

import br.com.hussan.cleanarch.AppNavigator
import br.com.hussan.cleanarch.data.mapper.FactViewMapper
import br.com.hussan.cleanarch.ui.main.FactsActivity
import br.com.hussan.cleanarch.ui.main.FactsViewModel
import br.com.hussan.cleanarch.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FactsViewModel(get(), get(), get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    single { (activity: FactsActivity) ->
        AppNavigator(activity = activity)
    }

    single {
        FactViewMapper()
    }
}
