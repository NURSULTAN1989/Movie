package com.example.movie.view

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movie.R
import com.example.movie.databinding.FragmentLoginBinding
import com.example.movie.viewmodel.LoginViewModel
import com.example.myfilms.data.models.LoginApprove
import java.lang.Exception

class LoginFragment: Fragment() {
    private  lateinit var binding: FragmentLoginBinding
    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings =
            context?.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE) as SharedPreferences
        editor = prefSettings.edit()
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        viewModel.deleteSession()
        onLoginClick()
    }
    private fun onLoginClick() {
        binding.logBtn.setOnClickListener {
            hideKeyboard(requireActivity())
            if (!binding.login.text.isNullOrBlank() && !binding.password.text.isNullOrBlank()) {
                val data = LoginApprove(
                    username = binding.login.text.toString().trim(),
                    password = binding.password.text.toString().trim(),
                    request_token = ""
                )
                viewModel.login(data)
                observeLoadingState()
            } else {
                Toast.makeText(requireContext(), "Введите данные", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun hideKeyboard(activity: Activity) {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
    }
    private fun initViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            )[LoginViewModel::class.java]
    }
    private fun observeLoadingState() {
        viewModel.sessionId.observe(viewLifecycleOwner) {
            if(!it.isNullOrBlank()){
                sessionId = it
                putDataIntoPref(sessionId)
                try {
                    findNavController().navigate(R.id.action_loginFragment_to_navigation_movies)
                } catch (e: Exception) {
                }
            } 

        }
    }
    private fun putDataIntoPref(string: String) {
        editor.putString(SESSION_ID_KEY, string)
        editor.commit()
        binding.login.text = null
        binding.password.text = null
    }
    companion object{
        private var sessionId: String = ""
        const val APP_SETTINGS = "Settings"
        const val SESSION_ID_KEY = "SESSION_ID"
    }
}