package com.example.movie.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.MovieRepositoryImpl
import com.example.movie.model.UserDB
import kotlinx.coroutines.launch

class UserViewModel(application: Application, private val repository: MovieRepositoryImpl) : AndroidViewModel(application) {

    private val context = application
//    private val repository = MovieRepositoryImpl(application)

    private val _user = MutableLiveData<UserDB>()
    val user: LiveData<UserDB>
        get() = _user

    fun getUser(session: String) {
        viewModelScope.launch {
            val userDB = repository.getUser(session) as UserDB
            if (!userDB.equals(null)) {
                _user.postValue(userDB)
            } else {
                Toast.makeText(
                    context,
                    "Требуется авторизация",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun updateUser(id: Int, uri: String) {
        viewModelScope.launch {
            _user.postValue(repository.updateUser(id, uri))
        }
    }

    fun deleteSession(session: String) {
        viewModelScope.launch {
            repository.deleteSession(session)
        }
    }
}