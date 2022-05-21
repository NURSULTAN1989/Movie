package com.example.movie.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movie.databinding.FragmentUserBinding
import com.example.movie.viewmodel.UserViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment:Fragment() {
    private lateinit var binding: FragmentUserBinding
    private val viewModel by viewModel<UserViewModel>()

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSessionId()
        onLogoutPressed()
        getUser()
        onEditClick()
    }

    private fun getUser(){
//        viewModel =
//            ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
//            )[UserViewModel::class.java]

        viewModel.getUser(sessionId)
        binding.swipeRefresh.isRefreshing = true
        viewModel.user.observe(
            viewLifecycleOwner
        ) {
            user_id=it.id
            username=it.username
            name=it.name
            binding.name.text = it.name
            binding.about.text = it.username
            val uri = Uri.parse(it?.uri_img)
            binding.imgUser.setImageURI(uri)
            binding.swipeRefresh.isRefreshing = false
        }
    }
    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }
    private fun onEditClick(){
        binding.editBtn.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .start()
        }
    }

    private fun onLogoutPressed() {
        binding.logOut.setOnClickListener {
            viewModel.deleteSession(sessionId)
            editor.clear().commit()
            findNavController().popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
            Activity.RESULT_OK -> {
                val uri = data?.data
                binding.imgUser.setImageURI(uri)
                val stringUri = uri.toString()
                viewModel.updateUser(user_id,stringUri)

//                val stringUri = uri.toString()
//                uri = Uri.parse(stringUri)
            }
        }
    }

    companion object {
        private var sessionId: String = ""
        private var user_id:Int=0
        private  var name = ""
        private var username = ""
    }
}