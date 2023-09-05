package se.linerotech.module202.guide.newsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import se.linerotech.module202.guide.R
import se.linerotech.module202.guide.common.News
import se.linerotech.module202.guide.databinding.LayoutCellNewsBinding

class NewsViewHolder(
    private val binding: LayoutCellNewsBinding,
    private val onCellClicked: (News) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News) {
        with(binding) {
            source.text = news.source
            date.text = news.date
            title.text = news.title
            root.setOnClickListener { onCellClicked(news) }
            loadImage(url = news.image)
        }
    }

    private fun loadImage(url: String) {
        if (url.isNotEmpty()) {
            binding.image.setImageResource(R.drawable.general_image)
        }
        Glide
            .with(binding.image)
            .load(url)
            .placeholder(R.drawable.general_image)
            .centerCrop()
            .into(binding.image)
    }

    companion object {
        fun create(parent: ViewGroup, onCellClicked: (News) -> Unit): NewsViewHolder {
            val binding =
                LayoutCellNewsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return NewsViewHolder(binding, onCellClicked)
        }
    }
}
