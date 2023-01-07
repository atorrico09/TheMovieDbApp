package com.atorrico.assignment.presentation.screen.movielist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atorrico.assignment.data.datasource.api.model.MovieApiModel
import com.atorrico.assignment.databinding.ItemMovieBinding
import com.atorrico.assignment.presentation.utils.Constants.BASE_URL_IMAGES
import com.bumptech.glide.Glide
import java.util.*

class MovieListAdapter(private val listener: MovieItemListener) :
    RecyclerView.Adapter<MovieViewHolder>() {

    interface MovieItemListener {
        fun onClickedMovie(movieId: Int)
    }

    private val items = ArrayList<MovieApiModel>()

    fun setItems(items: ArrayList<MovieApiModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(items[position])
}

class MovieViewHolder(
    private val itemBinding: ItemMovieBinding,
    private val listener: MovieListAdapter.MovieItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var movie: MovieApiModel

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: MovieApiModel) {
        this.movie = item
        itemBinding.tvTitle.text = item.title

        Glide.with(itemBinding.root)
            .load(BASE_URL_IMAGES + item.backdrop_path)
            .into(itemBinding.imgBackdrop)
    }

    override fun onClick(v: View?) {
        listener.onClickedMovie(movie.id)
    }
}
