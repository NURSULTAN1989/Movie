package com.example.movie.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.MovieRepositoryImpl
import com.example.myfilms.data.models.LoginApprove
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepositoryImpl(application)
    private val context = application



    private val _sessionId = MutableLiveData<String?>()
    val sessionId: LiveData<String?>
        get() = _sessionId

    fun deleteSession() {
        _sessionId.value = null
    }

    fun login(data: LoginApprove) {
        viewModelScope.launch {
            val sessionId = repository.login(data)
            if (sessionId.isNotBlank()){
                _sessionId.value = sessionId
            }else {
                   Toast.makeText(context, "Неверные данные", Toast.LENGTH_SHORT).show()
            }
        }
    }
}