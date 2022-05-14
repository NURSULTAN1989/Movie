package com.example.retrofitexample.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.model.Movie
import com.example.movie.model.UserDB

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Movie>)

    @Query("SELECT * FROM movie_table")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie_table WHERE id==:id")
    fun getMovieBiId(id:Int):Movie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserDB)

    @Query("SELECT * FROM user_table WHERE id==:id")
    fun getUser(id: Int): UserDB

    @Query("UPDATE user_table SET uri_img = :srcimg WHERE id=:id")
    fun updateUser(id: Int, srcimg: String)

}