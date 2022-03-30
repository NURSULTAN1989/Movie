package com.example.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // setAdapter()
       // getAllMovieList()
    }
    /*private fun setAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
    }
    override fun itemClick(position: Int, item: Movie) {
        val intent = Intent(this, MovieDetail::class.java)
        intent.putExtra("movie", item)


        startActivity(intent)
    }

    private fun getAllMovieList() {
        Common.getMovieApi().getMovieList().enqueue(object : Callback<List<Movie>> {
            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                Log.d("My_movie_list", response.body().toString())
                val list=response.body()
                binding.recyclerView.adapter =MyMovieAdapter(list = list, itemClick = this@MainActivity)
            }
        })
    }*/
}