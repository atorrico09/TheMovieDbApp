package com.atorrico.assignment.presentation.screen.movielist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atorrico.assignment.data.datasource.api.model.MovieWithGenre
import com.atorrico.assignment.databinding.ItemMovieBinding
import com.atorrico.assignment.presentation.utils.Constants.BASE_URL_IMAGES
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class MoviesAdapter(private val listener: MovieItemListener) : RecyclerView.Adapter<MovieViewHolder>() {

    interface MovieItemListener {
        fun onClickedMovie(movieId: Int)
    }

    private val items = ArrayList<MovieWithGenre>()

    fun setItems(items: ArrayList<MovieWithGenre>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(items[position])
}

class MovieViewHolder(private val itemBinding: ItemMovieBinding, private val listener: MoviesAdapter.MovieItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var movie: MovieWithGenre

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: MovieWithGenre) {
        this.movie = item
        itemBinding.tvTitle.text = item.title
        itemBinding.tvGenre.text = item.genre_name.toUpperCase(Locale.ROOT)

        Glide.with(itemBinding.root)
            .load(BASE_URL_IMAGES + item.backdrop_path)
            .into(itemBinding.imgBackdrop)
    }

    override fun onClick(v: View?) {
        listener.onClickedMovie(movie.id)
    }
}
