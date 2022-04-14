package com.example.movie.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.movie.R
import com.example.movie.databinding.FragmentLoginBinding
import com.example.movie.model.BodyRequest
import com.example.movie.model.Common
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class LoginFragment : Fragment(), CoroutineScope {
    lateinit var binding: FragmentLoginBinding
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createToken()

        binding.logBtn.setOnClickListener {
            if (binding.login.text.isNotEmpty() && binding.password.text.isNotEmpty()) {
                createToken()

            }
        }
        binding.regBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    fun createToken(){
        launch { val response = Common.getPostApi().createToken()
        val body=response.body()
            if (response.isSuccessful) {
                login( BodyRequest(
                    username = binding.login.text.toString(),
                    password = binding.password.text.toString(),
                    request_token = body?.request_token ?: ""))


            }

        }
    }
    fun login(bodyRequest: BodyRequest){
        launch {
            val body = Common.getPostApi().validateWithLogin(body = bodyRequest)
            if (body.isSuccessful) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}