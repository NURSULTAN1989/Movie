package com.example.movie.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movie.databinding.FragmentMainBinding
import com.example.movie.viewmodel.MovieListViewModel
import com.example.movie.viewmodel.ViewModelProviderFactory

class MainFragment : Fragment() {
    private lateinit var binding:FragmentMainBinding
    private lateinit var viewModel:MovieListViewModel
    private lateinit var adapter: MyMovieAdapter

    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding= FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        editor = prefSettings.edit()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSessionId()
        initAndObserveViewModel()
        binding.swipeRefresh.setOnRefreshListener {
            swipeRefresh()
        }
        onBackPressed()
    }
    private fun swipeRefresh() {
        initAndObserveViewModel()
    }

    private fun initAndObserveViewModel() {
        binding.swipeRefresh.isRefreshing = true
        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity())
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[MovieListViewModel::class.java]
        viewModel.liveData.observe(
            viewLifecycleOwner
        ) {
            val movie=it
            adapter = MyMovieAdapter(list = movie, viewModel.recyclerViewItemClickListener)
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
    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }
    private fun onBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                try {
                    viewModel.deleteSession(sessionId)
                    editor.clear().commit()
                    findNavController().popBackStack()
                } catch (e: Exception) {
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    companion object{
        private var sessionId: String = ""
    }
}