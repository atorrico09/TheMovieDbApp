package com.atorrico.assignment.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.databinding.FragmentMovieDetailBinding
import com.atorrico.assignment.utils.Constants.BASE_URL_IMAGES
import com.atorrico.assignment.utils.Resource
import com.atorrico.assignment.utils.autoCleared
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

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
        viewModel.movie.observe(viewLifecycleOwner, Observer {
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

        Glide.with(binding.root)
            .load(BASE_URL_IMAGES + movie.poster_path)
            .into(binding.imgPoster)
    }

    private fun getYear(releaseDate: String): String {
        val year = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate.parse(releaseDate).year.toString()
        } else {
            releaseDate.split("-")[0]
        }

        return year
    }
}
