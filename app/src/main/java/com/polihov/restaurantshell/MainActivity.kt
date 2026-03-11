package com.polihov.restaurantshell

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.polihov.restaurantshell.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurants = RestaurantRepository.getRestaurants()

        binding.recyclerRestaurants.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerRestaurants.adapter = RestaurantAdapter(restaurants) { restaurant ->
            val intent = Intent(this, RestaurantWebViewActivity::class.java).apply {
                putExtra(RestaurantWebViewActivity.EXTRA_NAME, restaurant.name)
                putExtra(RestaurantWebViewActivity.EXTRA_URL, restaurant.url)
            }
            startActivity(intent)
        }
    }
}
