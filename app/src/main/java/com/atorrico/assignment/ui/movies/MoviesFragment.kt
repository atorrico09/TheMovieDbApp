package com.atorrico.assignment.ui.movies

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.atorrico.assignment.R
import com.atorrico.assignment.databinding.FragmentMoviesBinding
import com.atorrico.assignment.utils.Resource
import com.atorrico.assignment.utils.autoCleared
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class MoviesFragment : Fragment(), MoviesAdapter.MovieItemListener, MyMoviesAdapter.MyMovieItemListener {

    private var binding: FragmentMoviesBinding by autoCleared()
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter
    private lateinit var adapter_my_movies: MyMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = 300.toLong()
        }
    }

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
        adapter_my_movies = MyMoviesAdapter(this)
        binding.subscribedRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        binding.subscribedRv.adapter = adapter_my_movies

        adapter = MoviesAdapter(this)
        binding.moviesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.movies.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })

        viewModel.myMovies.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                binding.tvSubscribed.visibility = View.VISIBLE
                binding.subscribedRv.visibility = View.VISIBLE
                adapter_my_movies.setItems(ArrayList(it))
            }else{
                binding.tvSubscribed.visibility = View.GONE
                binding.subscribedRv.visibility = View.GONE
            }
        })
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
