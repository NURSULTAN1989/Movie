package com.example.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso
import kotlinx.serialization.Serializable

class MyMovieAdapter(
    var list: List<Movie>
    ):RecyclerView.Adapter<MyMovieAdapter.MovieViewHolder>(){
   class MovieViewHolder(var binding:ItemLayoutBinding):RecyclerView.ViewHolder(binding.root)
    var listMovies = list
    lateinit var movieClick:MovieItemClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder.binding) {
            val movie = listMovies[position]
            txtName.text=movie.title
            Picasso.get().load(IMAGE_URL+movie.posterPath).into(imageMovie)
            executePendingBindings()
            root.setOnClickListener {
                movieClick.movieItemClick(movie)
                true
            }
        }
    }
    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    override fun getItemCount(): Int = list.size ?: 0

    interface MovieItemClick {

        fun movieItemClick(item: Movie)
    }
}