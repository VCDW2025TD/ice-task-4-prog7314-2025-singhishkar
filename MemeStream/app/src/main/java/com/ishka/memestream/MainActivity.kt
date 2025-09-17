package com.ishka.memestream

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ishka.memestream.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Default fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FeedFragment())
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            val fragment = when(menuItem.itemId) {
                R.id.nav_feed -> FeedFragment()
                R.id.nav_create -> CreateMemeFragment()
                R.id.nav_map -> MapFragment()
             //   R.id.nav_profile -> ProfileFragment()
                else -> null
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
            true
        }
    }
}
