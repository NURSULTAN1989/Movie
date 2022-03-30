package com.example.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso
import kotlinx.serialization.Serializable

class MyMovieAdapter(
    var list: List<Movie>?=null,
    val itemClick: RecyclerViewItemClick?=null
    ):RecyclerView.Adapter<MyMovieAdapter.MovieViewHolder>(){
    inner  class MovieViewHolder(item: View):RecyclerView.ViewHolder(item){
        val binding=ItemLayoutBinding.bind(item)
        fun bind(movie: Movie?){
            binding.data=movie
            Picasso.get().load(movie?.imageurl).into(binding.imageMovie)
            binding.executePendingBindings()
            binding.clMain.setOnClickListener {
                itemClick?.itemClick(adapterPosition, movie!!)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

    override fun getItemCount(): Int = list?.size ?: 0

    interface RecyclerViewItemClick {

        fun itemClick(position: Int, item: Movie)
    }
}