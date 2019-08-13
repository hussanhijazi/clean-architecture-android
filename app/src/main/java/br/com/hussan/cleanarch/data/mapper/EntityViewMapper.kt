package br.com.hussan.cleanarch.data.mapper

interface EntityViewMapper<T, V> {
    fun mapToView(type: V): T
}
