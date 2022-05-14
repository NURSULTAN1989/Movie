package com.example.movie.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movie.R
import com.example.movie.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.main_conteiner)
        initBottomNav()
        initOnDestinationChangedListener()
    }
    private fun initOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.mainFragment -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.favoriteFragment -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.loginFragment -> binding.bottomNavigation.visibility = View.GONE
                R.id.detailFragment -> binding.bottomNavigation.visibility = View.GONE
                R.id.detailFragment2 -> binding.bottomNavigation.visibility = View.GONE
            }
        }
    }

    private fun initBottomNav() {
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomNavigation.setupWithNavController(navController)
    }

}