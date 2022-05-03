package com.example.movie.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movie.R
import com.example.movie.databinding.ActivityMainBinding
import com.example.movie.viewmodel.ViewModelFavorites
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: ViewModelFavorites

    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefSettings = this.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        editor = prefSettings.edit()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navController = findNavController(R.id.main_conteiner)
        initBottomNav()
        initOnDestinationChangedListener()
        getSessionId()
        viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
            )[ViewModelFavorites::class.java]
        binding.button3.setOnClickListener {
            val builder= AlertDialog.Builder(this)
            builder.setMessage("Хотите ли вы выйти")
                .setPositiveButton("ДА",
                    DialogInterface.OnClickListener{ dialog, id ->
                            viewModel.deleteSession(sessionId)
                            editor.clear().commit()
                    })
                .setNegativeButton("Нет", DialogInterface.OnClickListener { dialog, id ->  })
                .create()
                .show()
        }
    }
    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }
    private fun initOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.mainFragment,
                R.id.favoriteFragment,
                -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    binding.button3.visibility = View.VISIBLE
                }
                R.id.detailFragment,
                R.id.loginFragment,
                -> {
                    binding.bottomNavigation.visibility = View.GONE
                    binding.button3.visibility = View.GONE

                }
            }
        }
    }

    private fun initBottomNav() {
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomNavigation.setupWithNavController(navController)
    }
    companion object {

        private var sessionId: String = ""
        private var PAGE = 1
    }

}