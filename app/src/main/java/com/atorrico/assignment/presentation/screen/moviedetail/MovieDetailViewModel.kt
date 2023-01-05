package com.atorrico.assignment.presentation.screen.moviedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.data.repository.MovieRepositoryImpl
import com.atorrico.assignment.presentation.utils.Result
import com.atorrico.assignment.presentation.utils.mapSuccess
import kotlinx.coroutines.Dispatchers

class MovieDetailViewModel @ViewModelInject constructor(
    private val repository: MovieRepositoryImpl
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

    suspend fun insert (movie: MovieEntityModel){
        repository.insertMovie(movie)
    }

    fun getMovie(id: Int) = repository.getMovieFavourite(id)

}
