package com.example.movie.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movie.databinding.FragmentUserBinding
import com.example.movie.viewmodel.MovieDetailViewModel
import com.example.movie.viewmodel.UserViewModel
import com.example.movie.viewmodel.ViewModelFavorites
import com.example.movie.viewmodel.ViewModelProviderFactory
import com.squareup.picasso.Picasso

class UserFragment:Fragment() {
    private lateinit var binding: FragmentUserBinding
    private lateinit var viewModel: UserViewModel

    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        editor = prefSettings.edit()
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSessionId()
        onLogoutPressed()
        getUser()
    }

    private fun getUser(){
        viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            )[UserViewModel::class.java]

        viewModel.getUser(sessionId)
        binding.swipeRefresh.isRefreshing = true
        viewModel.user.observe(
            viewLifecycleOwner
        ) {
            binding.name.text = it.name
            binding.about.text = it.username
            binding.swipeRefresh.isRefreshing = false
        }
    }
    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }
    private fun onLogoutPressed() {
        binding.logOut.setOnClickListener {
            viewModel.deleteSession(sessionId)
            editor.clear().commit()
            findNavController().popBackStack()
        }
    }
    companion object {
        private var sessionId: String = ""
    }
}