package com.atorrico.assignment.data.repository

import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.data.entities.MovieList
import com.atorrico.assignment.data.local.MovieDao
import com.atorrico.assignment.data.remote.MovieRemoteDataSource
import com.atorrico.assignment.utils.Result
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    suspend fun getMovie(id: Int): Result<Movie>{
        return remoteDataSource.getMovie(id)
    }

    suspend fun getMovies(): Result<MovieList>{
        return remoteDataSource.getMovies()
    }

    suspend fun getGenre(id: Int): Result<String>{
        return Result.Success(remoteDataSource.getGenre(id))
    }

    fun getMyMovies() = localDataSource.getMyMovies()

    suspend fun updateMovie(movie: Movie) = localDataSource.updateMovie(movie)

}