package br.com.hussan.cleanarch.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.hussan.cleanarch.extensions.spToPx

const val SMALL_FONT = 16F
const val BIG_FONT = 24F
const val MINIMUM_SIZE = 80

@BindingAdapter("setFontSize")
fun TextView.setFontSize(text: String?) {
    textSize = if (text?.length ?: 0 > MINIMUM_SIZE)
        SMALL_FONT.spToPx(context)
    else
        BIG_FONT.spToPx(context)
}

