package br.com.hussan.cleanarch.data.mapper

import br.com.hussan.cleanarch.data.model.FactViewModel
import br.com.hussan.cleanarch.domain.Fact

class FactViewMapper : EntityViewMapper<FactViewModel, Fact> {
    override fun mapToView(type: Fact): FactViewModel {
        return FactViewModel(
            type.id,
            type.value,
            type.category,
            type.query
        )
    }
}
