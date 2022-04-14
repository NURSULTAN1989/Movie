package com.example.movie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.movie.databinding.FragmentDetailBinding
import com.example.movie.model.Movie
import com.squareup.picasso.Picasso


class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var result: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentDetailBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        result = args.movie

        Picasso.get().load(IMAGE_URL + result.posterPath).into(binding.imageView2)
        binding.name.text=result.title
        binding.about.text = result.overview
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        parceArgs()
//    }
//    private fun parceArgs() {
//
//        requireArguments().getParcelable<Movie>(KEY)?.let {
//            result = it
//        }
//    }

    companion object {

      //  private const val KEY = "result"
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

//        fun newInstance(result: Movie):DetailFragment{
//
//            return DetailFragment().apply {
//
//                arguments = Bundle().apply {
//
//                    putParcelable(KEY, result)
//                }
//            }
//        }

    }
}