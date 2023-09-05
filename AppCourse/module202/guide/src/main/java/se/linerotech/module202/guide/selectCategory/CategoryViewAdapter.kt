package se.linerotech.module202.guide.selectCategory

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import se.linerotech.module202.guide.common.Category

class CategoryViewAdapter(
    private val items: List<Category>,
    private val onCellClicked: (Category) -> Unit,
) : RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.create(parent, onCellClicked)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = items[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
