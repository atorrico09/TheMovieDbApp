package com.atorrico.assignment.domain.repository

import androidx.lifecycle.LiveData
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.domain.model.Movie
import com.atorrico.assignment.presentation.util.Result

interface MovieRepository {

    suspend fun getMovie(id: Int): Result<Movie>

    suspend fun getMovieList(): Result<List<Movie>>

    fun getMovieDao(id: Int): LiveData<MovieEntityModel>

    fun getMovieListDao(): LiveData<List<MovieEntityModel>>

    suspend fun insertMovie(movie: MovieEntityModel)
}