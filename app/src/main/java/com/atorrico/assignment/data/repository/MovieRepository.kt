package com.atorrico.assignment.data.repository

import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.data.local.MovieDao
import com.atorrico.assignment.data.remote.MovieRemoteDataSource
import com.atorrico.assignment.utils.performGetOperation
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    fun getMovie(id: Int) = localDataSource.getMovie(id)

    fun getMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies() },
        networkCall = { remoteDataSource.getMovies() },
        saveCallResult = { localDataSource.insertAll(it.items) }
    )

    fun getMyMovies() = localDataSource.getMyMovies()

    suspend fun updateMovie(movie: Movie) = localDataSource.updateMovie(movie)

}