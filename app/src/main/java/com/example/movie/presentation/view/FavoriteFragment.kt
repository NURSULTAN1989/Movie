package com.example.movie.presentation.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movie.databinding.FavoriteFragmentBinding
import com.example.movie.presentation.viewmodel.ViewModelFavorites
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {
    private lateinit var binding: FavoriteFragmentBinding
    private val viewModel by viewModel<ViewModelFavorites>()
    private lateinit var adapter: MyMovieAdapter
    private val prefSettings: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding= FavoriteFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSessionId()
        initAndObserveViewModel()
    }
    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }

    private fun initAndObserveViewModel() {
//        viewModel =
//            ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
//            )[ViewModelFavorites::class.java]

        viewModel.getFavoriteMovie(sessionId, PAGE)
        binding.swipeRefresh.isRefreshing = true
        viewModel.movies.observe(
            viewLifecycleOwner
        ) {
            val movie=it
            adapter = MyMovieAdapter(list = movie, viewModel.recyclerViewItemClickListener)
            binding.fvRecyclerView.adapter = adapter
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.openDetail.observe(
            viewLifecycleOwner
        ) {
            it.getContentIfNotHandled()?.let { movie ->
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment22(movie.id)
                findNavController().navigate(action)
            }
        }
    }
    companion object {
        private var sessionId: String = ""
        private var PAGE = 1
    }
}