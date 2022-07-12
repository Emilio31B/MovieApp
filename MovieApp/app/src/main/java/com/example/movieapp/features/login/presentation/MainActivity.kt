package com.example.movieapp.features.login.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.movieapp.R
import com.example.movieapp.core.utils.setEmptyErrorMessage
import com.example.movieapp.core.utils.setTextWatcher
import com.example.movieapp.core.utils.showToastMessage
import com.example.movieapp.core.utils.toggleVisibility
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.features.login.domain.LoginRequest
import com.example.movieapp.features.movie.presentation.MovieActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        observerLiveData()
        setUpUI()
        setOnClick()
    }

    private fun observerLiveData() {
        viewModel.errorLD.observe(this) {

            this.showToastMessage( it.ifEmpty { getString(R.string.general_error) } )
        }

        viewModel.loadingLD.observe(this) {
            binding.progress.progressContainer.toggleVisibility(it)
        }

        viewModel.loginLD.observe(this) {
            if(it != null && it.status) {
                this.showToastMessage(it.message.ifEmpty { getString(R.string.success_login) })
                startActivity(Intent(this, MovieActivity::class.java))
                finish()
            }
        }
    }

    private fun setUpUI() {
        binding.tilUsername.setTextWatcher()
        binding.tilPassword.setTextWatcher()
    }

    private fun setOnClick() {
        binding.butSignIn.setOnClickListener {
            if(areEmptyFields()) {
                this.showToastMessage(getString(R.string.empty_fields_login))
            } else {
                viewModel.doLogin(
                    LoginRequest(
                        username = binding.tilUsername.editText?.text.toString(),
                        password = binding.tilPassword.editText?.text.toString()
                    )
                )
            }
        }
    }

    private fun areEmptyFields(): Boolean {
        return if(binding.tilUsername.editText?.text.toString().isEmpty() || binding.tilPassword.editText?.text.toString().isEmpty()) {
            binding.tilUsername.setEmptyErrorMessage(getString(R.string.username_error_msg))
            binding.tilPassword.setEmptyErrorMessage(getString(R.string.password_error_msg))
            true
        } else {
            false
        }
    }
}