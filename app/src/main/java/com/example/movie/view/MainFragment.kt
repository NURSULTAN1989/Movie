package com.example.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movie.databinding.FragmentMainBinding
import com.example.movie.viewmodel.MovieListViewModel

class MainFragment : Fragment() {
    private lateinit var binding:FragmentMainBinding
    private lateinit var viewModel:MovieListViewModel
    private lateinit var adapter: MyMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding= FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAndObserveViewModel()
    }

    private fun initAndObserveViewModel() {
        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        viewModel.liveData.observe(
            viewLifecycleOwner
        ) {
            val movie=it.results
            adapter = MyMovieAdapter(list = movie, viewModel.recyclerViewItemClickListener)
            binding.recyclerView.adapter = adapter
        }

        viewModel.openDetail.observe(
            viewLifecycleOwner
        ) {
            it.getContentIfNotHandled()?.let { movie ->
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id)
                findNavController().navigate(action)
            }

        }
    }
}