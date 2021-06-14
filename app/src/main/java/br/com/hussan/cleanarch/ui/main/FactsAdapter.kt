package br.com.hussan.cleanarch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.hussan.cleanarch.R
import br.com.hussan.cleanarch.data.model.FactViewModel
import br.com.hussan.cleanarch.databinding.ListItemFactBinding

class FactsAdapter(
    private val clickListenerShare: (FactViewModel) -> Unit,
    private val clickListenerGoToFact: (FactViewModel) -> Unit
) :
    RecyclerView.Adapter<FactsAdapter.FactViewHolder>() {

    private var factModels: List<FactViewModel> = listOf()

    inner class FactViewHolder(val binding: ListItemFactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ListItemFactBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item_fact, parent, false)
        return FactViewHolder(binding)
    }

    fun setItems(items: List<FactViewModel>) {
        factModels = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val fact = factModels[position]
        holder.binding.apply {
            this.fact = fact
            
            imgFactShare.setOnClickListener {
                clickListenerShare.invoke(fact)
            }

            root.setOnClickListener { clickListenerGoToFact(fact) }

            val cat = fact.category?.let { it } ?: listOf(root.context.getString(R.string.uncategorized))
            lytFactCategory.setData(cat, null)
        }
    }

    override fun getItemCount() = factModels.size

}
