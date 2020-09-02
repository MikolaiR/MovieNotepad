package com.example.mvvmroomvmodelldatadbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmroomvmodelldatadbinding.databinding.MovieListItemBinding
import com.example.mvvmroomvmodelldatadbinding.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    var movieList = listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MovieViewHolder(var movieListItemBinding: MovieListItemBinding) :
        RecyclerView.ViewHolder(movieListItemBinding.root) {

        init {
            movieListItemBinding.root.setOnClickListener {
                val position = adapterPosition
                onItemClickListener.onItemClick(movieList[position])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieListItemBinding = DataBindingUtil.inflate<MovieListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.movie_list_item,
            parent,
            false
        )
        return MovieViewHolder(movieListItemBinding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.movieListItemBinding.movie = movie
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}