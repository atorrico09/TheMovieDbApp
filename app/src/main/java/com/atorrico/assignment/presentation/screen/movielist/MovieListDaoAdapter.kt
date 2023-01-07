package com.atorrico.assignment.presentation.screen.movielist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.databinding.ItemMovieFavouriteBinding
import com.atorrico.assignment.presentation.util.Constants
import com.bumptech.glide.Glide

class MovieListDaoAdapter(private val clickListener: (MovieEntityModel) -> Unit) :
    RecyclerView.Adapter<MyMovieViewHolder>() {

    private val items = ArrayList<MovieEntityModel>()

    fun setItems(items: ArrayList<MovieEntityModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMovieViewHolder =
        MyMovieViewHolder(
            ItemMovieFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyMovieViewHolder, position: Int) =
        holder.bind(items[position], clickListener)
}

class MyMovieViewHolder(private val itemBinding: ItemMovieFavouriteBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var movie: MovieEntityModel

    @SuppressLint("SetTextI18n")
    fun bind(movieEntity: MovieEntityModel, clickListener: (MovieEntityModel) -> Unit) {
        this.movie = movieEntity

        Glide.with(itemBinding.root)
            .load(Constants.BASE_URL_IMAGES + movieEntity.poster_path)
            .into(itemBinding.imgPoster)

        itemBinding.root.setOnClickListener {
            clickListener(movieEntity)
        }
    }

}
