package com.example.astonfinalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.astonfinalproject.databinding.ActivityMainBinding
import com.example.astonfinalproject.presentation.screens.CharactersFragment
import com.example.astonfinalproject.presentation.screens.EpisodesFragment
import com.example.astonfinalproject.presentation.screens.LocationsFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener(this)
        setSupportActionBar(binding.topAppBar)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.bottom_navigation_characters_item -> {
                fragment = CharactersFragment()
            }

            R.id.bottom_navigation_locations_item -> {
                fragment = LocationsFragment()
            }

            R.id.bottom_navigation_episodes_item -> {
                fragment = EpisodesFragment()
            }
        }
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()
        }
        return true
    }
}