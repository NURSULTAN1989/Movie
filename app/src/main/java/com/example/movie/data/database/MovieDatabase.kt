package com.example.movie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movie.domain.entity.Movie
import com.example.movie.data.UserDB

@Database(entities = [Movie::class, UserDB::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun postDao(): MovieDao

    companion object {

        var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "app_database.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }

    }

}