package br.com.hussan.cleanarch.ui.search

import androidx.lifecycle.ViewModel
import br.com.hussan.cleanarch.usecases.GetCategories
import br.com.hussan.cleanarch.usecases.GetSearches

class SearchViewModel(
    private val getCategoriesCase: GetCategories,
    private val getSearchesCase: GetSearches
) : ViewModel() {

    fun getCategories() = getCategoriesCase.invoke()
    fun getSearches() = getSearchesCase.invoke()

}
