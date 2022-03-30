package com.example.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.databinding.ItemDetailBinding
import com.squareup.picasso.Picasso

class MovieDetail:AppCompatActivity() {
    private lateinit var binding: ItemDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val movie = intent.getParcelableExtra<Movie>("movie")
        binding.name.text = movie?.name.toString()
        binding.detail.text = movie?.bio.toString()
        Picasso.get().load(movie?.imageurl).into(binding.imageView)
    }

}