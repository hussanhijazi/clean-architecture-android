package br.com.hussan.cleanarch.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import br.com.hussan.cleanarch.R
import br.com.hussan.cleanarch.domain.Search
import br.com.hussan.cleanarch.extensions.add
import br.com.hussan.cleanarch.ui.main.FactsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {
    private val viewModel: SearchViewModel by viewModel()
    private val searchAdapter by lazy { SearchAdapter(::searchClicked) }
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupSearchRecyclerView()
        setupSearchListener()
        getCategories()
        getSearches()
    }

    private fun setupSearchRecyclerView() {
        rvSearches.run {
            adapter = searchAdapter
            val dividerItemDecoration = DividerItemDecoration(
                context,
                RecyclerView.VERTICAL
            )
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun getSearches() {
        viewModel.getSearches()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                searchAdapter.setItems(it)
            }, {})
            .add(compositeDisposable)
    }

    private fun getCategories() {
        viewModel.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                lytCategories.setData(it.map { it.name ?: "" }, ::categoryClicked)
            }, {})
            .add(compositeDisposable)
    }

    private fun categoryClicked(category: String) {
        setQuery(category)
    }

    private fun searchClicked(search: Search) {
        setQuery(search.query)
    }

    private fun setupSearchListener() {
        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                setQuery(edtSearch.text.toString())
            }
            false
        }
    }

    private fun setQuery(query: String) {
        val returnIntent = Intent()
        returnIntent.putExtra(FactsActivity.QUERY, query)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}
