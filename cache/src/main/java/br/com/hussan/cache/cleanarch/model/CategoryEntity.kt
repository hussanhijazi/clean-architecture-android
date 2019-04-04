package br.com.hussan.cache.cleanarch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") var name: String?
)
