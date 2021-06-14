package br.com.hussan.cleanarch.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FactViewModel(
    val id: String,
    val value: String,
    val category: List<String>? = null,
    val query: String = ""
) : Parcelable
