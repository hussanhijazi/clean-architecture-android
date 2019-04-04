package br.com.hussan.cleanarch.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.hussan.cleanarch.R
import br.com.hussan.cleanarch.databinding.ListItemSearchBinding
import br.com.hussan.cleanarch.domain.Search

class SearchAdapter(private val clickListenerShare: (Search) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var searches: List<Search> = listOf()

    inner class SearchViewHolder(val binding: ListItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding: ListItemSearchBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_search,
                parent, false
            )
        return SearchViewHolder(binding)
    }

    fun setItems(items: List<Search>) {
        searches = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val search = searches[position]
        holder.binding.search = search

        holder.binding.root.setOnClickListener {
            clickListenerShare.invoke(search)
        }
    }

    override fun getItemCount() = searches.size

    fun getSearches() = searches
}
