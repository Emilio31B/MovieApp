package com.example.movieapp.features.movie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.movieapp.R
import com.example.movieapp.core.utils.showToastMessage
import com.example.movieapp.databinding.ActivityMovieBinding
import com.example.movieapp.features.movie.domain.listmovie.ListMovieResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setUpNavigation()

    }

    private fun setUpNavigation() {
        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container_movie) as NavHostFragment?
        val navController = navHost!!.navController
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.movie_navigation)

        graph.setStartDestination(R.id.listMovieFragment)
        navController.setGraph(graph, null)
    }
}