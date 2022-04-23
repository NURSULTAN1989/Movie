package com.example.movie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movie.R
import com.example.movie.databinding.FragmentMainBinding
import com.example.movie.viewmodel.MovieListViewModel
import com.example.movie.viewmodel.ViewModelFavorites


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: ViewModelFavorites
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding= FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}