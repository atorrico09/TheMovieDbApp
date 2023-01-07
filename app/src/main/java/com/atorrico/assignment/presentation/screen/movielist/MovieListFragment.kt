package com.atorrico.assignment.presentation.screen.movielist

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.atorrico.assignment.R
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.databinding.FragmentMovieListBinding
import com.atorrico.assignment.domain.model.Movie
import com.atorrico.assignment.presentation.util.Result
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: MovieListAdapter
    private lateinit var adapterMovieFavourites: MovieListDaoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieListBinding.bind(view)

        setToolbarProperties()
        setupRecyclerViews()
        setupObservers()
    }

    private fun setupRecyclerViews() {
        adapterMovieFavourites = MovieListDaoAdapter { navigateToMovieDetailFavourite(it) }
        binding.rvFavourites.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFavourites.adapter = adapterMovieFavourites

        adapter = MovieListAdapter { navigateToMovieDetail(it) }
        binding.moviesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getMovieList().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (it.data != null)
                        adapter.setItems(it.data as ArrayList<Movie>)
                }
                is Result.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        it.exception?.message ?: getString(R.string.error_default_message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewModel.getMovieListDao().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding.tvFavourites.visibility = View.VISIBLE
                binding.rvFavourites.visibility = View.VISIBLE
                adapterMovieFavourites.setItems(ArrayList(it))
            } else {
                binding.tvFavourites.visibility = View.GONE
                binding.rvFavourites.visibility = View.GONE
            }
        }
    }

    private fun navigateToMovieDetail(movie: Movie) {
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 175.toLong()
        }

        findNavController().navigate(
            R.id.action_moviesFragment_to_movieDetailFragment,
            bundleOf("id" to movie.id)
        )
    }

    private fun navigateToMovieDetailFavourite(movieEntityModel: MovieEntityModel) {
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 175.toLong()
        }

        findNavController().navigate(
            R.id.action_moviesFragment_to_movieDetailFragment,
            bundleOf("id" to movieEntityModel.id)
        )
    }

    private fun setToolbarProperties() {
        val collapsing: View? = activity?.findViewById(R.id.collapsing_toolbar_layout)
        collapsing?.setBackgroundColor(Color.BLACK)
        collapsing?.imgToolbar?.visibility = View.GONE
    }
}
