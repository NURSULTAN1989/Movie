package com.example.movie.view

//import android.content.Intent
//import android.util.Log
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import kotlin.Result
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movie.databinding.FragmentMainBinding
import com.example.movie.model.MovieList
import com.example.movie.viewmodel.MovieListViewModel

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var adapter: MyMovieAdapter
    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // getAllMovieList()
        initAndObserveViewModel()


    }

    private fun initAndObserveViewModel() {
        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        viewModel.liveData.observe(viewLifecycleOwner) {
                    adapter = MyMovieAdapter(list = it.results)
                    binding.recyclerView.adapter = adapter
        }

        viewModel.openDetail.observe(
            viewLifecycleOwner
        ) {
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(it)
            findNavController().navigate(action)

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