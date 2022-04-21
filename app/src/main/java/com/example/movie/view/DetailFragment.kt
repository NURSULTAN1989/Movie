package com.example.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.movie.databinding.FragmentDetailBinding
import com.example.movie.viewmodel.MovieDetailViewModel
import com.example.movie.viewmodel.MovieListViewModel
import com.squareup.picasso.Picasso


class DetailFragment : Fragment() {
    private  lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding= FragmentDetailBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovie(args.movieId)
    }
    private fun getMovie(movieId: Int) {
        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        viewModel.getMovieById(movieId)
        viewModel.movie.observe(viewLifecycleOwner) {
            Picasso.get().load(IMAGE_URL + it.posterPath).into(binding.imageView2)
            binding.name.text=it.title
            binding.about.text = it.overview
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}