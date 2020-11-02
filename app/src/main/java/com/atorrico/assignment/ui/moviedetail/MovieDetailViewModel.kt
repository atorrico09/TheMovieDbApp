package com.atorrico.assignment.ui.moviedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.data.entities.MovieFavourite
import com.atorrico.assignment.data.repository.MovieRepository
import com.atorrico.assignment.utils.Result
import com.atorrico.assignment.utils.mapSuccess
import kotlinx.coroutines.Dispatchers

class MovieDetailViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private var id: Int = 0

    fun fetchMovie() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try{
            val movie = repository.getMovie(id).mapSuccess { it }
            emit(Result.Success(movie))
        }catch (e: Exception){
//            emit(Result.Failure(e))
        }
    }

    fun start(id: Int) {
        this.id = id
    }

    suspend fun insert (movie: MovieFavourite){
        repository.insertMovie(movie)
    }

    fun getMovie(id: Int) = repository.getMovieFavourite(id)

}
