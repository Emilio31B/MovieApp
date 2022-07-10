package com.example.movieapp.features.movie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMovieBinding
import com.example.movieapp.features.movie.domain.listmovie.ListMovieResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private val viewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        observerLiveData()
        viewModel.getListMovie(1)
    }


    private fun observerLiveData() {
        viewModel.errorLD.observe(this) {
            Toast.makeText(
                this,
                it.ifEmpty { getString(R.string.general_error) },
            Toast.LENGTH_SHORT).show()
        }

        viewModel.loadingLD.observe(this) { }

        viewModel.listMovieLD.observe(this) {
            if (it != null) {
                displayListMovie(it)
            }
        }
    }

    private fun displayListMovie(listMovie: ListMovieResponse) {
        binding.tvListMovie.text = listMovie.results[0].toString()
    }
}