package com.atorrico.assignment.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.atorrico.assignment.data.repository.MovieRepository

class MoviesViewModel @ViewModelInject constructor(
    repository: MovieRepository
) : ViewModel() {

    val movies = repository.getMovies()
    val myMovies = repository.getMyMovies()

}
