package br.com.hussan.cleanarch.usecases

import br.com.hussan.cleanarch.data.datasource.CategoryDatasource
import io.reactivex.Completable

class SaveCategories(private val dataSource: CategoryDatasource) {
    operator fun invoke(): Completable {
        return dataSource.saveCategories()
    }
}

