package com.example.movieapp.features.movie.presentation.listmovie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.core.utils.setImgWithGlide
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.features.movie.domain.listmovie.Movie

class ListMovieAdapter(
    //private var listMovie: List<Movie>,
    private val itemMovieClickListener: ItemMovieListener
): PagingDataAdapter<Movie, ListMovieAdapter.DataViewHolder>(MovieComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val movie = getItem(position)!!
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            itemMovieClickListener.onItemMovieClickListener(movie)
        }
    }

    /*override fun getItemCount(): Int {
        return listMovie.size
    }

    fun setListMovie(list: List<Movie>) {
        listMovie = list
    }*/

    class DataViewHolder(private val view: ItemMovieBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(movie: Movie) {
            with(view) {
                imgMovie.setImgWithGlide(movie.poster_path ?: "")
                tvMovieName.text = movie.original_title
            }
        }
    }
    /*inner class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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
    }*/

    object MovieComparator: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.original_title == newItem.original_title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

}