package br.com.hussan.cache.cleanarch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fact")
data class FactEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "value") var value: String,
    @ColumnInfo(name = "term") var query: String
)
