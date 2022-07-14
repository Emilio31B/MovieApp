package com.example.movieapp.features.movie.presentation.listmovie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.core.utils.Constants
import com.example.movieapp.core.utils.showToastMessage
import com.example.movieapp.core.utils.toggleVisibility
import com.example.movieapp.databinding.FragmentListMovieBinding
import com.example.movieapp.features.movie.domain.moviedescription.Movie
import com.example.movieapp.features.movie.presentation.MovieViewModel
import com.example.movieapp.features.movie.presentation.listmovie.adapter.ItemMovieListener
import com.example.movieapp.features.movie.presentation.listmovie.adapter.ListMovieAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListMovieFragment : Fragment(), ItemMovieListener {

    private var _binding: FragmentListMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: ListMovieAdapter
    private val viewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        observerLiveData()
        setOnClick()
        getListMovie()
    }

    private fun setAdapter() {
        binding.listMovieRecyclerView.layoutManager = LinearLayoutManager(context)
        movieAdapter = ListMovieAdapter(this)

        binding.listMovieRecyclerView.apply {
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (this.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = movieAdapter
        }
        viewModel.setAdapterStateListener(movieAdapter)
    }

    private fun setOnClick() {
        binding.floatRetryButton.setOnClickListener { refreshListMovie() }
    }

    private fun getListMovie() {
        viewModel.getListMovie().observe(viewLifecycleOwner) {
            it?.let {
                movieAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun observerLiveData() {
        viewModel.errorLD.observe(viewLifecycleOwner) {
            context?.showToastMessage( it.ifEmpty { getString(R.string.general_error) } )
            binding.floatRetryButton.toggleVisibility(true)
        }

        viewModel.loadingLD.observe(viewLifecycleOwner) {
            binding.progress.progressContainer.toggleVisibility(it)
        }

        viewModel.emptyListMovieLD.observe(viewLifecycleOwner) {
            binding.emptyListMovieState.toggleVisibility(it)
            binding.listMovieRecyclerView.toggleVisibility(!it)
            binding.floatRetryButton.toggleVisibility(it)
        }
    }

    override fun onItemMovieClickListener(movie: Movie) {
        val navBuilder = NavOptions.Builder()
        navBuilder
            .setEnterAnim(R.anim.slide_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.slide_out)
        val bundle = Bundle()
        bundle.putSerializable(Constants.extra_data, movie)
        findNavController().navigate(
            R.id.movieDescriptionFragment, bundle, navBuilder.build())
    }

    private fun refreshListMovie() {
        movieAdapter.retry()
        binding.emptyListMovieState.toggleVisibility(false)
        binding.listMovieRecyclerView.toggleVisibility(true)
        binding.floatRetryButton.toggleVisibility(false)
    }

}