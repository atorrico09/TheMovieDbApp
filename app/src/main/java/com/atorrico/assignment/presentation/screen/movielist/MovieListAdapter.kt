package com.atorrico.assignment.presentation.screen.movielist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atorrico.assignment.databinding.ItemMovieBinding
import com.atorrico.assignment.domain.model.Movie
import com.atorrico.assignment.presentation.util.Constants.BASE_URL_IMAGES
import com.bumptech.glide.Glide
import java.util.*

class MovieListAdapter(private val clickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val items = ArrayList<Movie>()

    fun setItems(items: ArrayList<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(items[position], clickListener)
}

class MovieViewHolder(private val itemBinding: ItemMovieBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var movie: Movie

    @SuppressLint("SetTextI18n")
    fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
        this.movie = movie
        itemBinding.tvTitle.text = movie.title

        Glide.with(itemBinding.root)
            .load(BASE_URL_IMAGES + movie.backdropPath)
            .into(itemBinding.imgBackdrop)

        itemBinding.root.setOnClickListener {
            clickListener(movie)
        }
    }
}
