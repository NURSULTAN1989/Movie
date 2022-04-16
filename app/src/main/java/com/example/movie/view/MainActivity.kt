package com.example.movie.view

//import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movie.R
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import com.example.movie.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

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
                R.id.mainFragment,
                R.id.second_fragment
                -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.detailFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    private fun initBottomNav() {
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomNavigation.setupWithNavController(navController)
    }

}