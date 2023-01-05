package com.atorrico.assignment.presentation.screen.movielist

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atorrico.assignment.R
import com.atorrico.assignment.data.datasource.api.model.MovieWithGenre
import com.atorrico.assignment.databinding.FragmentMoviesBinding
import com.atorrico.assignment.presentation.utils.Result
import com.atorrico.assignment.presentation.utils.autoCleared
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class MoviesFragment : Fragment(), MoviesAdapter.MovieItemListener,
    MoviesFavouritesAdapter.MyMovieItemListener {

    private var binding: FragmentMoviesBinding by autoCleared()
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter
    private lateinit var adapterFavs: MoviesFavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarProperties()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapterFavs = MoviesFavouritesAdapter(this)
        binding.subscribedRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.subscribedRv.adapter = adapterFavs

        adapter = MoviesAdapter(this)
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.moviesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.fetchMovies().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading ->
                    binding.progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data != null)
                        adapter.setItems(it.data as ArrayList<MovieWithGenre>)
                }
                is Result.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.moviesFavourites.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding.tvSubscribed.visibility = View.VISIBLE
                binding.subscribedRv.visibility = View.VISIBLE
                adapterFavs.setItems(ArrayList(it))
            } else {
                binding.tvSubscribed.visibility = View.GONE
                binding.subscribedRv.visibility = View.GONE
            }
        }
    }

    override fun onClickedMovie(movieId: Int) {
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 175.toLong()
        }

        findNavController().navigate(
            R.id.action_moviesFragment_to_movieDetailFragment,
            bundleOf("id" to movieId)
        )
    }

    private fun setToolbarProperties() {
        val collapsing: View? = activity?.findViewById(R.id.collapsing_toolbar_layout)
        collapsing?.setBackgroundColor(Color.BLACK)
        collapsing?.imgToolbar?.visibility = View.GONE
    }

    override fun onClickedMyMovie(movieId: Int) {
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 175.toLong()
        }

        findNavController().navigate(
            R.id.action_moviesFragment_to_movieDetailFragment,
            bundleOf("id" to movieId)
        )
    }
}
