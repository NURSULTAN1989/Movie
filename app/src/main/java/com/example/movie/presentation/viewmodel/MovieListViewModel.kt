package com.example.movie.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.domain.Event
import com.example.movie.domain.entity.Movie
import com.example.movie.presentation.view.MyMovieAdapter
import com.example.movie.data.database.MovieDao
import com.example.movie.data.database.MovieDatabase
import com.example.movie.domain.exception.ApiError
import com.example.movie.domain.usecases.GetAllMoviesListUseCase
import com.example.retrofitexample.domain.common.UseCaseResponse
import kotlinx.coroutines.launch

class MovieListViewModel(application: Application, private val getMovieList: GetAllMoviesListUseCase) : AndroidViewModel(application){
//    private val repository = MovieRepositoryImpl(application)
    private val movieDao: MovieDao


    private val _liveData = MutableLiveData<List<Movie>>()
    val liveData: MutableLiveData<List<Movie>>
        get() = _liveData

    private val _openDetail = MutableLiveData<Event<Movie>>()
    val openDetail: LiveData<Event<Movie>>
        get() = _openDetail

    private val _showError = MutableLiveData<ApiError>()
    val showError: LiveData<ApiError>
        get() = _showError


    init {
        getAllMovieListCoroutine()
        movieDao= MovieDatabase.getDatabase(application).postDao()
    }

    private fun getAllMovieListCoroutine() {
            getMovieList.invoke(viewModelScope,null, null, object : UseCaseResponse<List<Movie>> {
                override fun onSuccess(result: List<Movie>) {
                    _liveData.value =result
                }

                override fun onError(apiError: ApiError?) {
                    _showError.value = apiError!!
                }
            })
//        viewModelScope.launch {  _liveData.value = getMovieList.getAllMoviesList() }
    }
    val recyclerViewItemClickListener = object : MyMovieAdapter.MovieItemClick {
        override fun movieItemClick(item: Movie) {
            _openDetail.value = Event(item)
        }
    }
}