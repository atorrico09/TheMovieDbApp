package com.atorrico.assignment.presentation.screen.movielist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.databinding.ItemMovieFavouriteBinding
import com.atorrico.assignment.presentation.util.Constants
import com.bumptech.glide.Glide

class MovieListDaoAdapter(private val listener: MyMovieItemListener) :
    RecyclerView.Adapter<MyMovieViewHolder>() {

    interface MyMovieItemListener {
        fun onClickedMovieFavourite(movieId: Int)
    }

    private val items = ArrayList<MovieEntityModel>()

    fun setItems(items: ArrayList<MovieEntityModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMovieViewHolder {
        val binding: ItemMovieFavouriteBinding =
            ItemMovieFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyMovieViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyMovieViewHolder, position: Int) =
        holder.bind(items[position])
}

class MyMovieViewHolder(
    private val itemBinding: ItemMovieFavouriteBinding,
    private val listener: MovieListDaoAdapter.MyMovieItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var movie: MovieEntityModel

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: MovieEntityModel) {
        this.movie = item

        Glide.with(itemBinding.root)
            .load(Constants.BASE_URL_IMAGES + item.poster_path)
            .into(itemBinding.imgPoster)
    }

    override fun onClick(v: View?) {
        listener.onClickedMovieFavourite(movie.id)
    }
}
