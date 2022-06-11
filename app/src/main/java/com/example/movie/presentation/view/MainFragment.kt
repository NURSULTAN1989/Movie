package com.example.movie.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movie.databinding.FragmentMainBinding
import com.example.movie.presentation.viewmodel.MovieListViewModel
import com.example.movie.presentation.viewmodel.MovieListViewModelObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModelObserver: MovieListViewModelObserver
    private val viewModel by viewModel<MovieListViewModel>()
    private lateinit var adapter: MyMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAndObserveViewModel()
        binding.swipeRefresh.setOnRefreshListener {
            swipeRefresh()
        }
    }

    private fun swipeRefresh() {
        initAndObserveViewModel()
    }

    private fun initAndObserveViewModel() {
        binding.swipeRefresh.isRefreshing = true
//        viewModelObserver = MovieListViewModelObserver(
//            viewModel = viewModel,
//            viewLifecycleOwner = this,
//            liveData = {
//                adapter = MyMovieAdapter(list = it, viewModel.recyclerViewItemClickListener)
//                binding.recyclerView.adapter = adapter
//                binding.swipeRefresh.isRefreshing = false
//            },
//            openDetail = {
//                it.getContentIfNotHandled()?.let { movie ->
//                    val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id)
//                    findNavController().navigate(action)
//                }
//            },
//            showError = {
//                binding.tvError.text = it.message
//            }
//
//        )


//        viewModel =
//            ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
//            )[MovieListViewModel::class.java]

        viewModel.liveData.observe(
            viewLifecycleOwner
        ) {
            adapter = MyMovieAdapter(list = it, viewModel.recyclerViewItemClickListener)
            binding.recyclerView.adapter = adapter
            binding.swipeRefresh.isRefreshing = false
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