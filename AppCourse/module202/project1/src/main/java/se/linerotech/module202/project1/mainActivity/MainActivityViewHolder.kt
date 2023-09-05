package se.linerotech.module202.project1.mainActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import se.linerotech.module202.project1.data.DataCities
import se.linerotech.module202.project1.databinding.LayoutCellActivityMainBinding

class MainActivityViewHolder(
    private val binding: LayoutCellActivityMainBinding,
    private val onCellClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(city: DataCities) {
        binding.imageView.setImageResource(city.image)
        binding.cardView.setOnClickListener { onCellClick(city.name) }
    }

    companion object {
        fun create(parent: ViewGroup, onCellClick: (Int) -> Unit): MainActivityViewHolder {
            val binding = LayoutCellActivityMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MainActivityViewHolder(binding, onCellClick)
        }
    }
}
