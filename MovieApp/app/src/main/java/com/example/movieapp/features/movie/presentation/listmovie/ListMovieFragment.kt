package com.example.movieapp.features.movie.presentation.listmovie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.core.utils.Constants
import com.example.movieapp.core.utils.showToastMessage
import com.example.movieapp.core.utils.toggleVisibility
import com.example.movieapp.databinding.FragmentListMovieBinding
import com.example.movieapp.features.movie.domain.listmovie.Movie
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
        setUpSwipeToRefresh()
        viewModel.getListMovie(1)
    }

    private fun setAdapter() {
        binding.listMovieRecyclerView.layoutManager = LinearLayoutManager(context)
        movieAdapter = ListMovieAdapter(emptyList(), this)

        binding.listMovieRecyclerView.apply {
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (this.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = movieAdapter
        }
    }

    private fun observerLiveData() {
        viewModel.errorLD.observe(viewLifecycleOwner) {
            context?.showToastMessage( it.ifEmpty { getString(R.string.general_error) } )
            binding.errorListMovieState.toggleVisibility(true)
            binding.listMovieRecyclerView.toggleVisibility(false)
            binding.emptyListMovieState.toggleVisibility(false)
        }

        viewModel.loadingLD.observe(viewLifecycleOwner) {
            binding.progress.progressContainer.toggleVisibility(it)
        }

        viewModel.listMovieLD.observe(viewLifecycleOwner) {
            if (it != null && it.results.isNotEmpty()) {
                renderListMovie(it.results)
            } else {
                binding.emptyListMovieState.toggleVisibility(true)
                binding.listMovieRecyclerView.toggleVisibility(false)
                binding.errorListMovieState.toggleVisibility(false)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderListMovie(list: List<Movie>) {
        movieAdapter.apply {
            setListMovie(list)
            notifyDataSetChanged()
        }

    }

    override fun onItemMovieClickListener(movie: Movie) {
        findNavController().navigate(R.id.movieDescriptionFragment)
    }

    private fun setUpSwipeToRefresh() {
        binding.strRoot.apply {
            setColorSchemeResources(R.color.teal_700)
            setOnRefreshListener {
                renderListMovie(emptyList())
                refreshListMovie()
                lifecycleScope.launch {
                    delay(Constants.swipe_to_refresh_hide_time)
                    binding.strRoot.isRefreshing = false
                }
            }
        }
    }

    private fun refreshListMovie() {
        viewModel.getListMovie(1)
        binding.emptyListMovieState.toggleVisibility(false)
        binding.listMovieRecyclerView.toggleVisibility(true)
        binding.errorListMovieState.toggleVisibility(false)
    }

}