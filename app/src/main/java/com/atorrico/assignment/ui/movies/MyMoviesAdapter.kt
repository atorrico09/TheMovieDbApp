package com.atorrico.assignment.ui.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.databinding.ItemMyMovieBinding
import com.atorrico.assignment.utils.Constants
import com.bumptech.glide.Glide

class MyMoviesAdapter(private val listener: MyMovieItemListener) : RecyclerView.Adapter<MyMovieViewHolder>() {

    interface MyMovieItemListener {
        fun onClickedMyMovie(movieId: Int)
    }

    private val items = ArrayList<Movie>()

    fun setItems(items: ArrayList<Movie>) {
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

class MyMovieViewHolder(private val itemBinding: ItemMyMovieBinding, private val listener: MyMoviesAdapter.MyMovieItemListener) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

    private lateinit var movie: Movie

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Movie) {
        this.movie = item

        Glide.with(itemBinding.root)
                .load(Constants.BASE_URL_IMAGES + item.poster_path)
                .into(itemBinding.imgPoster)
    }

    override fun onClick(v: View?) {
        listener.onClickedMyMovie(movie.id)
    }
}
