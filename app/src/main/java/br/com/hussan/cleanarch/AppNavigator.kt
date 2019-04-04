package br.com.hussan.cleanarch

import android.app.Activity
import br.com.hussan.cleanarch.extensions.navigateForResult
import br.com.hussan.cleanarch.ui.search.SearchActivity

class AppNavigator(private val activity: Activity) {

    fun goToSearch(requestCode: Int) {
        activity.navigateForResult<SearchActivity>(requestCode)
    }
}
