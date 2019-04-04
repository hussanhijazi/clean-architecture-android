package br.com.hussan.cache.cleanarch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "search")
data class SearchEntity(
    @PrimaryKey
    @ColumnInfo(name = "query") var query: String,
    var searchedAt: Date
)
