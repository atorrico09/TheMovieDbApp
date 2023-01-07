package com.atorrico.assignment.presentation.screen.moviedetail

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.atorrico.assignment.R
import com.atorrico.assignment.data.datasource.api.model.MovieApiModel
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.databinding.FragmentMovieDetailBinding
import com.atorrico.assignment.presentation.util.Constants.BASE_URL_IMAGES
import com.atorrico.assignment.presentation.util.Result
import com.atorrico.assignment.presentation.util.autoCleared
import com.atorrico.assignment.presentation.util.getDominantColor
import com.atorrico.assignment.presentation.util.getYear
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.fragment_movie_detail.view.*
import kotlin.properties.Delegates


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var binding: FragmentMovieDetailBinding by autoCleared()
    private val viewModel: MovieDetailViewModel by viewModels()
    private var movieId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough().apply {
            duration = 300.toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieId = arguments?.getInt("id") ?: 0
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getMovie(movieId).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    bindMovie(it.data as MovieApiModel)
                    binding.progressBar.visibility = View.GONE
                    binding.movieCl.visibility = View.VISIBLE
                }
                is Result.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun bindMovie(movieApiModel: MovieApiModel) {
        binding.tvTitle.text = movieApiModel.title
        binding.tvYear.text = getYear(movieApiModel.release_date)
        binding.tvOverview.text = movieApiModel.overview

        Glide.with(this)
            .asBitmap()
            .load(BASE_URL_IMAGES + movieApiModel.poster_path)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val dominantColor = getDominantColor(resource)
                    view?.nested_detail?.setBackgroundColor(dominantColor)
                    setToolbarProperties(dominantColor, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

        btnSuscribe.setOnClickListener {
            var isSubscribed = false
            if (binding.btnSuscribe.text == resources.getString(R.string.button_suscribe_text))
                isSubscribed = true

            val movieDetail =
                MovieEntityModel(movieApiModel.id, movieApiModel.poster_path, isSubscribed)

            viewModel.insertMovieDao(movieDetail)
        }

        viewModel.getMovieDao(movieId).observe(viewLifecycleOwner) {
            if (it != null && it.subscribe) {
                binding.btnSuscribe.text = resources.getString(R.string.label_suscribed_text)
                binding.btnSuscribe.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
                binding.btnSuscribe.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.transparent
                    )
                )
            } else {
                binding.btnSuscribe.text = resources.getString(R.string.button_suscribe_text)
                binding.btnSuscribe.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.transparent
                    )
                )
                binding.btnSuscribe.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }
    }

    private fun setToolbarProperties(dominantColor: Int, resource: Bitmap) {
        val collapsing: View? = activity?.findViewById(R.id.collapsing_toolbar_layout)
        collapsing?.imgToolbar?.setImageBitmap(resource)
        collapsing?.imgToolbar?.visibility = View.VISIBLE
        collapsing?.setBackgroundColor(dominantColor)
    }

}
