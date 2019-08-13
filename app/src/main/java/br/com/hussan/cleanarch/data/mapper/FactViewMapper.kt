package br.com.hussan.cleanarch.data.mapper

import br.com.hussan.cleanarch.data.model.FactView
import br.com.hussan.cleanarch.domain.Fact

class FactViewMapper : EntityViewMapper<FactView, Fact> {
    override fun mapToView(type: Fact): FactView {
        return FactView(
            type.id,
            type.value,
            type.category,
            type.query
        )
    }
}
