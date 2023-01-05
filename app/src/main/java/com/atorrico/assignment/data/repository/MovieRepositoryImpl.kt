package com.atorrico.assignment.data.repository

import com.atorrico.assignment.data.datasource.api.response.GetGenreResponse
import com.atorrico.assignment.data.datasource.api.model.MovieApiModel
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.data.datasource.api.response.GetMovieResponse
import com.atorrico.assignment.data.datasource.database.MovieDao
import com.atorrico.assignment.data.datasource.api.MovieRemoteDataSource
import com.atorrico.assignment.presentation.utils.Result
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    suspend fun getMovie(id: Int): Result<MovieApiModel> {
        return remoteDataSource.getMovie(id)
    }

    suspend fun getMovies(): Result<GetMovieResponse> {
        return remoteDataSource.getMovies()
    }

    suspend fun getGenres(): Result<GetGenreResponse> {
        return remoteDataSource.getGenres()
    }

    fun getAllMovies() = localDataSource.getAllMovies()
    fun getMovieFavourite(id: Int) = localDataSource.getMovie(id)
    suspend fun insertMovie(movie: MovieEntityModel) = localDataSource.insert(movie)

}