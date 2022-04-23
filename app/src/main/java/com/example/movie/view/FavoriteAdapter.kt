package com.example.movie.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.databinding.ItemLayoutBinding
import com.example.movie.model.Movie
import com.squareup.picasso.Picasso

class FavoriteAdapter(var list: List<Movie>, var movieClick: MovieItemClick
): RecyclerView.Adapter<FavoriteAdapter.FavoriteMovieViewHolder>(){
    class FavoriteMovieViewHolder(var binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root)
    private var listMovies = list
    //lateinit var movieClick: MovieItemClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        return FavoriteMovieViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        with(holder.binding) {
            val movie = listMovies[position]
            txtName.text=movie.title
            Picasso.get().load(IMAGE_URL +movie.posterPath).into(imageMovie)
            executePendingBindings()
            root.setOnClickListener {
                movieClick.movieItemClick(movie)
            }
        }
    }
    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    override fun getItemCount(): Int = list.size

    interface MovieItemClick {

        fun movieItemClick(item: Movie)
    }
}