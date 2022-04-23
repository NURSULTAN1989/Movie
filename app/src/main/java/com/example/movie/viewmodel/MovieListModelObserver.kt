package com.example.movie.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.movie.model.Event
import com.example.movie.model.Movie
import com.example.movie.view.MainFragment

class MovieListModelObserver(private val context: Context,
                             private val viewModel: MovieListViewModel,
                             private val viewLifecycleOwner: LifecycleOwner,

                             private val openDetail: ((post: Event<Movie>) -> Unit),
                             private val liveData: ((state: List<Movie>) -> Unit)
) {

    init {
        observeViewModel()
    }

    private fun observeViewModel() {
        liveData.apply {
            viewModel.liveData.observe(
                viewLifecycleOwner
            ) {
                this.invoke(it)
            }
        }

        openDetail.apply {
            viewModel.openDetail.observe(
                viewLifecycleOwner
            ) {
                this.invoke(it)
            }
        }
    }

}