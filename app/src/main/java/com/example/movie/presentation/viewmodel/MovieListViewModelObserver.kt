package com.example.movie.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.example.movie.domain.Event
import com.example.movie.domain.entity.Movie
import com.example.movie.domain.exception.ApiError

class MovieListViewModelObserver(
    private val viewModel: MovieListViewModel,
    private val viewLifecycleOwner: LifecycleOwner,

    private val showError: ((apiError: ApiError) -> Unit),
    private val openDetail: ((movie: Event<Movie>) -> Unit),
    private val liveData: ((movieList: List<Movie>) -> Unit)
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

        showError.apply {
            viewModel.showError.observe(
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