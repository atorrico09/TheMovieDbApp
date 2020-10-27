package com.atorrico.assignment.data.remote

import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
): BaseDataSource() {

    suspend fun getMovies() = getResult { movieService.getAllMovies() }
    suspend fun getMovie(id: Int) = getResult { movieService.getMovie(id) }
}