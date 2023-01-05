package com.atorrico.assignment.data.datasource.api

import com.atorrico.assignment.data.datasource.api.response.GetGenreResponse
import com.atorrico.assignment.data.datasource.api.model.MovieApiModel
import com.atorrico.assignment.data.datasource.api.response.GetMovieResponse
import com.atorrico.assignment.data.datasource.api.service.MovieRetrofitService
import com.atorrico.assignment.presentation.utils.Result
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieRetrofitService: MovieRetrofitService
) {

    suspend fun getMovies(): Result<GetMovieResponse> = Result.Success(movieRetrofitService.getAllMovies())
    suspend fun getMovie(id: Int): Result<MovieApiModel> = Result.Success (movieRetrofitService.getMovie(id))
    suspend fun getGenres(): Result<GetGenreResponse> = Result.Success(movieRetrofitService.getGenres())
}