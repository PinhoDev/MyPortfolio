package se.linerotech.module202.project1.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import se.linerotech.module202.project1.R
import se.linerotech.module202.project1.data.DataCities
import se.linerotech.module202.project1.databinding.LayoutActivityMainBinding
import se.linerotech.module202.project1.weatherActivity.WeatherActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LayoutActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolBar()
        showImageOfCity()
    }

    private fun configureToolBar() {
        setSupportActionBar(binding.tollBar)
        supportActionBar?.title = getString(R.string.select_a_city)
    }

    private fun showImageOfCity() {
        binding.recyclerView.apply {
            adapter =
                MainActivityViewAdapter(items = detailOfCities(), onCellClick = ::showDetailOfCity)
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
        }
    }

    private fun detailOfCities(): List<DataCities> {
        return listOf(
            DataCities(name = R.string.agra, image = R.drawable.agra),
            DataCities(name = R.string.barcelona, image = R.drawable.barcelona),
            DataCities(name = R.string.beijing, image = R.drawable.beijing),
            DataCities(name = R.string.sydney, image = R.drawable.sydney),
            DataCities(name = R.string.paris, image = R.drawable.paris),
            DataCities(name = R.string.new_york, image = R.drawable.new_york),
            DataCities(name = R.string.london, image = R.drawable.london),
            DataCities(name = R.string.rio_de_janeiro, image = R.drawable.rio_de_janeiro),
            DataCities(name = R.string.moscow, image = R.drawable.moscow),
            DataCities(name = R.string.rome, image = R.drawable.rome),
        )
    }

    private fun showDetailOfCity(city: Int) {
        val intent = WeatherActivity.intent(this, city)
        startActivity(intent)
    }
}
