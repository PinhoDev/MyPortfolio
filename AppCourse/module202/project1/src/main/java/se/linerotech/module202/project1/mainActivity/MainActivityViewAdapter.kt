package se.linerotech.module202.project1.mainActivity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import se.linerotech.module202.project1.data.DataCities

class MainActivityViewAdapter(
    private val items: List<DataCities>,
    private val onCellClick: (Int) -> Unit,
) : RecyclerView.Adapter<MainActivityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        return MainActivityViewHolder.create(parent, onCellClick)
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        val cities = items[position]
        holder.bind(cities)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
