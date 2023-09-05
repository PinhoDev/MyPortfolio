package se.linerotech.module202.guide.selectCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import se.linerotech.module202.guide.common.Category
import se.linerotech.module202.guide.databinding.LayoutCellCategoryBinding

class CategoryViewHolder(
    private val binding: LayoutCellCategoryBinding,
    private val onCellClicked: (Category) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category) {
        with(binding) {
            categoryName.setText(category.name)
            cardView.setBackgroundResource(category.image)
            cardView.setOnClickListener { onCellClicked(category) }
        }
    }

    companion object {
        fun create(parent: ViewGroup, onCellClicked: (Category) -> Unit): CategoryViewHolder {
            val binding =
                LayoutCellCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return CategoryViewHolder(binding, onCellClicked)
        }
    }
}
