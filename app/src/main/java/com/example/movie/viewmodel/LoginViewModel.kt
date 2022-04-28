package com.example.movie.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.model.Common
import com.example.myfilms.data.models.LoginApprove
import com.example.myfilms.data.models.Session
import com.example.myfilms.data.models.Token
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application
    private val apiService = Common.getInstance()


    private val _sessionId = MutableLiveData<String>()
    val sessionId: LiveData<String>
        get() = _sessionId

    fun login(data: LoginApprove) {

        viewModelScope.launch {
            val responseGet = apiService.getToken()
            if (responseGet.isSuccessful) {
                val loginApprove = LoginApprove(
                    username = data.username,
                    password = data.password,
                    request_token = responseGet.body()?.request_token as String
                )
                val responseApprove = apiService.approveToken(loginApprove = loginApprove)
                if (responseApprove.isSuccessful) {
                    val session =
                        apiService.createSession(token = responseApprove.body() as Token)
                    if (session.isSuccessful) {
                        _sessionId.value = session.body()?.session_id
                    }
                } else {
                    Toast.makeText(context, "Неверные данные", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun deleteSession(session: String) {
        viewModelScope.launch {
            apiService.deleteSession(sessionId = Session(session_id = session))
        }
    }
}