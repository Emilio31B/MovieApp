package com.example.movieapp.features.movie.presentation.listmovie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.core.utils.setImgWithGlide
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.features.movie.domain.listmovie.Movie

class ListMovieAdapter(
    private var listMovie: List<Movie>,
    private val itemMovieClickListener: ItemMovieListener
): RecyclerView.Adapter<ListMovieAdapter.DataViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DataViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
    )

    override fun onBindViewHolder(holder: ListMovieAdapter.DataViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    fun setListMovie(list: List<Movie>) {
        listMovie = list
    }

    inner class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)

        fun bind(movie: Movie) {
            with(binding) {
                imgMovie.setImgWithGlide(movie.poster_path)
                tvMovieName.text = movie.title
            }
            itemView.setOnClickListener {
                itemMovieClickListener.onItemMovieClickListener(movie)
            }
        }
    }

}