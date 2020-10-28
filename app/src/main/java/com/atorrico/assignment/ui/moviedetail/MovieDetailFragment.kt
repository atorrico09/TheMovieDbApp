package com.atorrico.assignment.ui.moviedetail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.atorrico.assignment.R
import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.databinding.FragmentMovieDetailBinding
import com.atorrico.assignment.utils.Constants.BASE_URL_IMAGES
import com.atorrico.assignment.utils.Resource
import com.atorrico.assignment.utils.autoCleared
import com.atorrico.assignment.utils.getDominantColor
import com.atorrico.assignment.utils.getYear
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_movie_detail.view.*


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var binding: FragmentMovieDetailBinding by autoCleared()
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()


    }

    private fun setupObservers() {
        viewModel.movie.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindMovie(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.movieCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.movieCl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindMovie(movie: Movie) {
        binding.tvTitle.text = movie.title
        binding.tvYear.text = getYear(movie.release_date)
        binding.tvOverview.text = movie.overview

        Glide.with(this)
            .asBitmap()
            .load(BASE_URL_IMAGES + movie.poster_path)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val dominantColor = getDominantColor(resource)
                    view?.nested_detail?.setBackgroundColor(dominantColor)
                    setToolbarProperties(dominantColor, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })
    }

    private fun setToolbarProperties(dominantColor: Int, resource: Bitmap) {
        val collapsing: View? = activity?.findViewById(R.id.collapsing_toolbar_layout)
        collapsing?.imgToolbar?.setImageBitmap(resource)
        collapsing?.imgToolbar?.visibility = View.VISIBLE
        collapsing?.setBackgroundColor(dominantColor)
    }

}
