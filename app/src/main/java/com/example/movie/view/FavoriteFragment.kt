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
import com.example.movie.databinding.FavoriteFragmentBinding
import com.example.movie.viewmodel.ViewModelFavorites


class FavoriteFragment : Fragment() {
    private lateinit var binding: FavoriteFragmentBinding
    private lateinit var viewModel: ViewModelFavorites
    private lateinit var adapter: MyMovieAdapter

    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        editor = prefSettings.edit()
        super.onCreate(savedInstanceState)
    }

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
        onBackPressed()

    }
    //получить session id из SharedPreference
    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }

    //создаем ViewModel и устанвливаем observe на наши LiveData
    private fun initAndObserveViewModel() {

        //создаем ViewModel текущего фрагмента
        viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            )[ViewModelFavorites::class.java]

        //один раз прогружаем данные (это можно сделать внутри ViewModel через init(), но в моем случае так не получистя)
        viewModel.downloadData(sessionId, PAGE)
        binding.swipeRefresh.isRefreshing = true
        //следим за loadingState внутри ViewModel
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
    private fun onBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                try {
                    viewModel.deleteSession(sessionId)
                    editor.clear().commit()
                    findNavController().popBackStack()
                } catch (e: Exception) {
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    companion object {

        private var sessionId: String = ""
        private var PAGE = 1
    }
}