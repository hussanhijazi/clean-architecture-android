package br.com.hussan.cleanarch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.hussan.cleanarch.R
import br.com.hussan.cleanarch.databinding.ListItemFactBinding
import br.com.hussan.cleanarch.domain.Fact

class FactsAdapter(private val clickListenerShare: (Fact) -> Unit) :
    RecyclerView.Adapter<FactsAdapter.FactViewHolder>() {

    private var facts: List<Fact> = listOf()

    inner class FactViewHolder(val binding: ListItemFactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ListItemFactBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item_fact, parent, false)
        return FactViewHolder(binding)
    }

    fun setItems(items: List<Fact>) {
        facts = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val fact = facts[position]
        holder.binding.fact = fact

        holder.binding.root.setOnClickListener {
            clickListenerShare.invoke(fact)
        }

        val cat = fact.category?.let { it } ?: listOf(holder.binding.root.context.getString(R.string.uncategorized))

        holder.binding.lytFactCategory.setData(cat, null)
    }

    override fun getItemCount() = facts.size

}
