package com.example.movie.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.model.Common
import com.example.movie.model.User
import com.example.movie.model.UserDB
import com.example.myfilms.data.models.Session
import com.example.retrofitexample.model.database.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserViewModel(val context: Context) : ViewModel() {


    private val apiService = Common.getInstance()
    private val userDao = MovieDatabase.getDatabase(context).postDao()

    private val _user = MutableLiveData<UserDB>()
    val user: LiveData<UserDB>
        get() = _user

    fun getUser(session: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                val response = apiService.getAccount(session_id = session)
                if (response.isSuccessful) {
                    val result = response.body() as User
                        val resultDB = userDao.getUser(result.id)
                        if (resultDB != null) {
                            _user.postValue(resultDB)
                            println("ERROR USER")
                        } else {
                    val userDB = UserDB(result.id, result.name, result.username, "")
                            userDao.insertUser(userDB)
                    _user.postValue(userDB)
                    println("SUCCESS USER")
                        }
                } else {
                    Toast.makeText(
                        context,
                        "Требуется авторизация",
                        Toast.LENGTH_SHORT
                    ).show()
                    }
                } catch (e: Exception) {
                }
            }
        }
    }
        fun updateUser(id: Int, uri: String) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    userDao.updateUser(id, uri)
                    _user.postValue(userDao.getUser(id))
                }
            }
        }

        fun deleteSession(session: String) {
            viewModelScope.launch {
                apiService.deleteSession(sessionId = Session(session_id = session))
            }
        }
}