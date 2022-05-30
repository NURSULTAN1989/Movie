package com.example.movie.presentation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.domain.entity.LoginApprove
import com.example.movie.domain.entity.Movie
import com.example.movie.domain.exception.ApiError
import com.example.movie.domain.usecases.LoginUseCase
import com.example.retrofitexample.domain.common.UseCaseResponse
import kotlinx.coroutines.launch

class LoginViewModel(application: Application, private val logUseCase: LoginUseCase) : AndroidViewModel(application) {
//    private val repository = MovieRepositoryImpl(application)
    private val context = application


    private val _sessionId = MutableLiveData<String?>()
    val sessionId: LiveData<String?>
        get() = _sessionId

    private val _showError = MutableLiveData<ApiError>()
    val showError: LiveData<ApiError>
        get() = _showError

    fun deleteSession() {
        _sessionId.value = null
    }

    fun login(data: LoginApprove) {
//        logUseCase.invoke(viewModelScope,data, null, object : UseCaseResponse<String> {
//            override fun onSuccess(result: String) {
//                _sessionId.value = result
//            }
//
//            override fun onError(apiError: ApiError?) {
//                Toast.makeText(context, "Неверные данные", Toast.LENGTH_SHORT).show()
//            }
//        })
        viewModelScope.launch {
            val sessionId = logUseCase.login(data)
            if (sessionId.isNotBlank()){
                _sessionId.value = sessionId
            }else {
                   Toast.makeText(context, "Неверные данные", Toast.LENGTH_SHORT).show()
            }
        }
    }
}