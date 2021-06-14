package br.com.hussan.cleanarch.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import br.com.hussan.cleanarch.AppNavigator
import br.com.hussan.cleanarch.R
import br.com.hussan.cleanarch.data.model.FactViewModel
import br.com.hussan.cleanarch.databinding.ActivityFactsBinding
import br.com.hussan.cleanarch.extensions.add
import br.com.hussan.cleanarch.extensions.hide
import br.com.hussan.cleanarch.extensions.show
import br.com.hussan.cleanarch.extensions.snack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FactsActivity : AppCompatActivity() {

    private val viewModel: FactsViewModel by viewModel()
    private val navigator: AppNavigator by inject { parametersOf(this@FactsActivity) }
    private val compositeDisposable = CompositeDisposable()

    private lateinit var binding: ActivityFactsBinding

    companion object {
        const val SEARCH_REQUEST = 1
        const val QUERY = "query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_facts
        )

        getCategories()

        if (savedInstanceState == null)
            getRandomFacts()

    }

    @Preview
    @Composable
    private fun Loading() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            CircularProgressIndicator()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                navigator.goToSearch(SEARCH_REQUEST)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SEARCH_REQUEST) {
//                factsAdapter.setItems(listOf())
                binding.lytEmptyState.hide()
                data?.getStringExtra(QUERY)?.let {
                    getFacts(it)
                }
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.let {
//            factsAdapter.setItems(viewModel.resultsFacts.value ?: return)
        }
    }

    private fun getFacts(query: String) {
        viewModel.getFacts(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .subscribe(::showFacts, ::showError)
            .add(compositeDisposable)
    }

    private fun getRandomFacts() {
        viewModel.getRandomFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .subscribe({ setFacts(it) }, ::showError)
            .add(compositeDisposable)
    }
    
    private fun setFacts(factModels: List<FactViewModel>) {
        setContent { FactsList(factModels = factModels) }
    }
    
    @Composable
    private fun FactsList(factModels: List<FactViewModel>) {
        LazyColumn {
            items(factModels) { fact ->
                FactCard(fact)
            }
        }
    }

    @Composable
    private fun FactCard(factModel: FactViewModel) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { goToFact(factModel = factModel) })
        ) {
            Text(
                text = factModel.value,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    private fun getCategories() {
        viewModel.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .add(compositeDisposable)
    }

    private fun showFacts(list: List<FactViewModel>) {
        if (list.isNotEmpty()) {
//            factsAdapter.setItems(list)
            showRecyclerViewFacts()
        } else
            showEmptyState()
    }

    private fun showEmptyState() {
        binding.rvFacts.hide()
        binding.lytEmptyState.show()
    }

    private fun showRecyclerViewFacts() {
        binding.rvFacts.show()
        binding.lytEmptyState.hide()
    }

    private fun showError(error: Throwable) {
        setContent {
            SnackBar()
        }
    }

    @Composable
    private fun SnackBar() {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        val modifier = Modifier

        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = getString(R.string.error_message))
        }

        Box(modifier.fillMaxSize()) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

    private fun showLoading() {
        setContent {
            Loading()
        }
    }

    private fun shareFact(factModel: FactViewModel) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, factModel.value)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
    }

    private fun goToFact(factModel: FactViewModel) {
        navigator.goToFact(factModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
