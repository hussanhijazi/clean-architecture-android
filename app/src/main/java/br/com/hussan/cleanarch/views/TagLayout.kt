package br.com.hussan.cleanarch.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import br.com.hussan.cleanarch.R
import br.com.hussan.cleanarch.databinding.LytFactCategoriesBinding
import com.google.android.material.chip.ChipGroup

class TagLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ChipGroup(context, attrs, defStyleAttr) {

    private var tags = listOf<String>()

    fun getItem(position: Int): String {
        return tags[position]
    }

    fun setData(tags: List<String>, clickListener: ((String) -> Unit)?) {
        this.tags = tags
        removeAllViews()
        tags.forEach { category ->
            category.run {
                val factCategoryBinding: LytFactCategoriesBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(context),
                        R.layout.lyt_fact_categories,
                        null,
                        false
                    )

                factCategoryBinding.category = category
                clickListener?.let { factCategoryBinding.root.setOnClickListener { it(category) } }

                addView(factCategoryBinding.root)
            }
        }
    }
}

