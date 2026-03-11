package com.polihov.restaurantshell

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.polihov.restaurantshell.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences

    companion object {
        private const val PREFS_NAME = "restaurant_app_prefs"
        private const val KEY_LAST_RESTAURANT_ID = "last_restaurant_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        val lastRestaurantId = prefs.getInt(KEY_LAST_RESTAURANT_ID, -1)
        val lastRestaurant = RestaurantRepository.getRestaurantById(lastRestaurantId)

        if (lastRestaurant != null) {
            openRestaurant(lastRestaurant)
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurants = RestaurantRepository.getRestaurants()

        binding.recyclerRestaurants.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerRestaurants.adapter = RestaurantAdapter(restaurants) { restaurant ->
            saveLastRestaurant(restaurant.id)
            openRestaurant(restaurant)
        }
    }

    private fun saveLastRestaurant(restaurantId: Int) {
        prefs.edit()
            .putInt(KEY_LAST_RESTAURANT_ID, restaurantId)
            .apply()
    }

    private fun openRestaurant(restaurant: Restaurant) {
        val intent = Intent(this, RestaurantWebViewActivity::class.java).apply {
            putExtra(RestaurantWebViewActivity.EXTRA_NAME, restaurant.name)
            putExtra(RestaurantWebViewActivity.EXTRA_URL, restaurant.url)
        }
        startActivity(intent)
    }
}
