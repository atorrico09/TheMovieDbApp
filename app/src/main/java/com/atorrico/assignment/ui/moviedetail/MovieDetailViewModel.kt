package com.atorrico.assignment.ui.moviedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.data.repository.MovieRepository
import com.atorrico.assignment.utils.Resource

class MovieDetailViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _movie = _id.switchMap { id ->
        repository.getMovie(id)
    }
    val movie: LiveData<Resource<Movie>> = _movie


    fun start(id: Int) {
        _id.value = id
    }

}
