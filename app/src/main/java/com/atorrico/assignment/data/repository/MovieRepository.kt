package com.atorrico.assignment.data.repository

import com.atorrico.assignment.data.entities.GenreList
import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.data.entities.MovieFavourite
import com.atorrico.assignment.data.entities.MovieList
import com.atorrico.assignment.data.local.MovieFavouritesDao
import com.atorrico.assignment.data.remote.MovieRemoteDataSource
import com.atorrico.assignment.utils.Result
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieFavouritesDao
) {

    suspend fun getMovie(id: Int): Result<Movie>{
        return remoteDataSource.getMovie(id)
    }

    suspend fun getMovies(): Result<MovieList>{
        return remoteDataSource.getMovies()
    }

    suspend fun getGenres(): Result<GenreList>{
        return remoteDataSource.getGenres()
    }

    fun getAllMovies() = localDataSource.getAllMovies()
    fun getMovieFavourite(id: Int) = localDataSource.getMovie(id)
    suspend fun insertMovie(movie: MovieFavourite) = localDataSource.insert(movie)

}