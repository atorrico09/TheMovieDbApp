package com.atorrico.assignment.ui.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atorrico.assignment.data.entities.MovieFavourite
import com.atorrico.assignment.databinding.ItemMyMovieBinding
import com.atorrico.assignment.utils.Constants
import com.bumptech.glide.Glide

class MoviesFavouritesAdapter(private val listener: MyMovieItemListener) : RecyclerView.Adapter<MyMovieViewHolder>() {

    interface MyMovieItemListener {
        fun onClickedMyMovie(movieId: Int)
    }

    private val items = ArrayList<MovieFavourite>()

    fun setItems(items: ArrayList<MovieFavourite>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMovieViewHolder {
        val binding: ItemMyMovieBinding = ItemMyMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyMovieViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyMovieViewHolder, position: Int) = holder.bind(items[position])
}

class MyMovieViewHolder(private val itemBinding: ItemMyMovieBinding, private val listener: MoviesFavouritesAdapter.MyMovieItemListener) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

    private lateinit var movie: MovieFavourite

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: MovieFavourite) {
        this.movie = item

        Glide.with(itemBinding.root)
                .load(Constants.BASE_URL_IMAGES + item.poster_path)
                .into(itemBinding.imgPoster)
    }

    override fun onClick(v: View?) {
        listener.onClickedMyMovie(movie.id)
    }
}
