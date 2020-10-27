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
import com.atorrico.assignment.utils.Resource
import com.atorrico.assignment.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

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
        binding.tvYear.text = movie.release_date
        binding.tvOverview.text = movie.overview

//        Glide.with(binding.root)
//            .load(movie.image)
//            .transform(CircleCrop())
//            .into(binding.image)
    }
}
