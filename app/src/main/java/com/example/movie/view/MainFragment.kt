package com.example.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movie.databinding.FragmentMainBinding
import com.example.movie.model.Movie
import com.example.movie.viewmodel.MovieListViewModel

class MainFragment : Fragment() {
    private lateinit var binding:FragmentMainBinding
    private lateinit var viewModel:MovieListViewModel
    private lateinit var adapter: MyMovieAdapter

    //override val coroutineContext: CoroutineContext = Dispatchers.Main

 //   lateinit var movie: MovieList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding= FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getAllMovieList()
       // getAllMovieListCoroutine()
        initAndObserveViewModel()
    }

    /*private fun getAllMovieListCoroutine() {

        launch {
            adapter= MyMovieAdapter(list=movie.results)
            binding.recyclerView.adapter =adapter

            adapter.movieClick=object: MyMovieAdapter.MovieItemClick {
                override fun movieItemClick(item: Movie) {
                    val action = MainFragmentDirections.actionMainFragment2ToDetailFragment2(item)
                    findNavController().navigate(action)
                }
            }
        }
    }*/

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

    private fun initAndObserveViewModel() {
        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]

        viewModel.liveData.observe(
            viewLifecycleOwner
        ) {
            adapter = MyMovieAdapter(list = it.results,viewModel.recyclerViewItemClickListener)
            binding.recyclerView.adapter = adapter
            /*adapter.movieClick=object: MyMovieAdapter.MovieItemClick {
                override fun movieItemClick(item: Movie) {
                    val action = MainFragmentDirections.actionMainFragmentToDetailFragment(item)
                    findNavController().navigate(action)
                }
            }*/
        }

        viewModel.openDetail.observe(
            viewLifecycleOwner
        ) {
            it.getContentIfNotHandled()?.let {
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }
        }
    }
}