package com.example.movie.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.movie.R
import com.example.movie.databinding.FragmentDetailBinding
import com.example.movie.viewmodel.MovieDetailViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment() {
    private  lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel by viewModel<MovieDetailViewModel>()
    private lateinit var prefSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding= FragmentDetailBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId=args.movieId
        getSessionId()
        getMovie(movieId)
        onFavoriteClickListener()
    }
    private fun getMovie(movieId: Int) {
        binding.swipeRefresh.isRefreshing=true
//        viewModel =
//            ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
//            )[MovieDetailViewModel::class.java]
        viewModel.getMovieById(movieId)
        viewModel.movie.observe(viewLifecycleOwner) {
            Picasso.get().load(IMAGE_URL + it.posterPath).into(binding.imageView2)
            imgActive()
            binding.name.text=it.title
            binding.about.text = it.overview
            binding.swipeRefresh.isRefreshing=false
        }
    }
    private fun imgActive(){
        binding.swipeRefresh.isRefreshing=true
        viewModel.composeFavorite(sessionId, movieId)
        viewModel.compose.observe(viewLifecycleOwner){
            if (it){
                binding.imageView3.setImageResource(R.drawable.btn_star_big_on_pressed)
                binding.swipeRefresh.isRefreshing=false
            }else {
                binding.imageView3.setImageResource(R.drawable.btn_star_big_off_pressed)
                binding.swipeRefresh.isRefreshing=false
            }
        }
    }
    private fun addFavorite(movieId: Int, sessionId: String) {
        viewModel.addFavorite(movieId, sessionId)
        viewModel.addFavoriteState.observe(viewLifecycleOwner) {
            if (it){
                binding.imageView3.setImageResource(R.drawable.btn_star_big_on_pressed)
                binding.imageView3.tag = TAG_YELLOW
            }

        }
    }
    private fun deleteFavorite(movieId: Int, sessionId: String) {
        viewModel.deleteFavorites(movieId, sessionId)
        viewModel.addFavoriteState.observe(viewLifecycleOwner) {
            if (it){
                binding.imageView3.setImageResource(R.drawable.btn_star_big_off_pressed)
                binding.imageView3.tag = TAG_WHITE
            }
        }
    }
    private fun onFavoriteClickListener() {

        binding.imageView3.setOnClickListener {
            if (binding.imageView3.tag == TAG_YELLOW) {
                deleteFavorite(movieId, sessionId)
            } else {
                addFavorite(movieId, sessionId)
            }
        }
    }
    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        private const val TAG_WHITE = "white"
        private const val TAG_YELLOW = "yellow"
        private var movieId: Int = 0
        private var sessionId: String = ""
    }
}