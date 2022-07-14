package com.example.movieapp.features.movie.presentation.moviedescription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.core.utils.*
import com.example.movieapp.databinding.FragmentMovieDescriptionBinding
import com.example.movieapp.features.movie.domain.moviedescription.Movie
import com.example.movieapp.features.movie.presentation.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDescriptionFragment : Fragment() {

    private var _binding: FragmentMovieDescriptionBinding? = null
    private val binding get() = _binding!!
    private var currentMovie: Movie? = null
    private val viewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressed()
        getCurrentMovie()
        observerLivedata()
        getMovieDescription()
    }

    private fun getCurrentMovie() {
        currentMovie = arguments?.getSerializable(Constants.extra_data) as Movie
    }

    private fun getMovieDescription() {
        currentMovie?.let { viewModel.getMovieDescription(it.id) }
    }

    private fun observerLivedata() {
        viewModel.errorLD.observe(viewLifecycleOwner) {
            context?.showToastMessage( it.ifEmpty { getString(R.string.general_error) })
            findNavController().popBackStack()
        }

        viewModel.loadingLD.observe(viewLifecycleOwner) {
            binding.progress.progressContainer.toggleVisibility(it)
            binding.scrollContainer.toggleVisibility(!it)
        }

        viewModel.movieDescLD.observe(viewLifecycleOwner) {
            if (it != null) {
                setMovieDescription(it)
            }
        }
    }

    private fun setMovieDescription(movie: Movie) {
        binding.apply {
            posterMovie.setImgWithGlide(movie.poster_path ?: "")
            tvTitle.text = movie.title ?: ""
            ratingMovie.rating = movie.vote_average?.toRatingFloat() ?: 0f
            tvReleaseDate.text = movie.release_date ?: ""
            tvOverview.text = movie.overview ?: ""
        }
    }


    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(isEnabled) {
                    isEnabled = false
                    findNavController().popBackStack()
                }
            }
        })
    }
}