package com.atorrico.assignment.data.remote

import com.atorrico.assignment.data.entities.GenreList
import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.data.entities.MovieList
import com.atorrico.assignment.utils.Result
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) {

    suspend fun getMovies(): Result<MovieList> = Result.Success(movieService.getAllMovies())
    suspend fun getMovie(id: Int): Result<Movie> = Result.Success (movieService.getMovie(id))
    suspend fun getGenres(): Result<GenreList> = Result.Success(movieService.getGenres())
}