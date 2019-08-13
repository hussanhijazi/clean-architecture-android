package br.com.hussan.cleanarch

import android.app.Activity
import android.os.Bundle
import br.com.hussan.cleanarch.data.model.FactView
import br.com.hussan.cleanarch.extensions.navigate
import br.com.hussan.cleanarch.extensions.navigateForResult
import br.com.hussan.cleanarch.ui.detailsfact.DetailFactActivity
import br.com.hussan.cleanarch.ui.search.SearchActivity

class AppNavigator(private val activity: Activity) {

    companion object {
        val FACT = "FACT"
    }

    fun goToSearch(requestCode: Int) {
        activity.navigateForResult<SearchActivity>(requestCode)
    }

    fun goToFact(fact: FactView) {
        activity.navigate<DetailFactActivity>(Bundle().apply {
            putParcelable(FACT, fact)
        })
    }
}
