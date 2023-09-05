package se.linerotech.module202.guide.newsList

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import se.linerotech.module202.guide.common.News

class NewsViewAdapter(
    private val items: List<News>,
    private val onCellClicked: (News) -> Unit,
) : RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent, onCellClicked)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = items[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
