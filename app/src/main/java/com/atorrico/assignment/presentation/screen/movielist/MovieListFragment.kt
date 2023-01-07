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
import com.atorrico.assignment.data.datasource.api.model.MovieApiModel
import com.atorrico.assignment.databinding.FragmentMovieListBinding
import com.atorrico.assignment.presentation.utils.Result
import com.atorrico.assignment.presentation.utils.autoCleared
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class MovieListFragment : Fragment(), MovieListAdapter.MovieItemListener,
    MovieListDaoAdapter.MyMovieItemListener {

    private var binding: FragmentMovieListBinding by autoCleared()
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var adapter: MovieListAdapter
    private lateinit var adapterMovieFavourites: MovieListDaoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarProperties()
        setupRecyclerViews()
        setupObservers()
    }

    private fun setupRecyclerViews() {
        adapterMovieFavourites = MovieListDaoAdapter(this)
        binding.rvFavourites.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFavourites.adapter = adapterMovieFavourites

        adapter = MovieListAdapter(this)
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.moviesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getMovieList().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data != null)
                        adapter.setItems(it.data as ArrayList<MovieApiModel>)
                }
                is Result.Loading ->
                    binding.progressBar.visibility = View.VISIBLE
                is Result.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
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

    override fun onClickedMovieFavourite(movieId: Int) {
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 175.toLong()
        }

        findNavController().navigate(
            R.id.action_moviesFragment_to_movieDetailFragment,
            bundleOf("id" to movieId)
        )
    }
}
