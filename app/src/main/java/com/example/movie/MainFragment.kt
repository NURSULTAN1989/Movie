package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Result
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment(), CoroutineScope {
    lateinit var binding:FragmentMainBinding
    lateinit var adapter: MyMovieAdapter

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    lateinit var movie: MovieList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getAllMovieList()
        getAllMovieListCoroutine()
    }

    private fun getAllMovieListCoroutine() {

        launch {
            movie = Common.getPostApi().getMoviesList()

            adapter=MyMovieAdapter(list=movie.results)
            binding.recyclerView.adapter =adapter

            adapter.movieClick=object:MyMovieAdapter.MovieItemClick{
                override fun movieItemClick(item: Movie) {
                    val action = MainFragmentDirections.actionMainFragment2ToDetailFragment2(item)
                    findNavController().navigate(action)
                }
            }
        }
    }

//    private fun getAllMovieList1() {
//        Common.getPostApi().getMoviesList().enqueue(object : Callback<MovieList> {
//            override fun onFailure(call: Call<MovieList>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
//                Log.d("My_movie_list", response.body().toString())
//                val list=response.body()
//                adapter=MyMovieAdapter(list=list?.results as List<Movie>)
//                binding.recyclerView.adapter =adapter
//                adapter.movieClick=object:MyMovieAdapter.MovieItemClick{
//                    override fun movieItemClick(item: Movie) {
////                 //       detailFragment(item)
//                        val action = MainFragmentDirections.actionMainFragment2ToDetailFragment2(item)
//                        findNavController().navigate(action)
//                    }
//                }
//            }
//        })
//    }


//    private fun detailFragment(movie: Movie) {
//        requireActivity().supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.main_conteiner, DetailFragment.newInstance(movie))
//            .addToBackStack(null)
//            .commit()
//    }


}