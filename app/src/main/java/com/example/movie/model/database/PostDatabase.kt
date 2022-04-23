package com.example.retrofitexample.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movie.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): MovieDao

    companion object {

        var INSTANCE: PostDatabase? = null

        fun getDatabase(context: Context): PostDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    PostDatabase::class.java,
                    "app_database.db"
                ).build()
            }
            return INSTANCE!!
        }

    }

}