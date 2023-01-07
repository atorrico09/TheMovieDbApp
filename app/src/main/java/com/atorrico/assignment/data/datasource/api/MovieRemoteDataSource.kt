package com.atorrico.assignment.data.datasource.api

import com.atorrico.assignment.data.datasource.api.model.MovieApiModel
import com.atorrico.assignment.data.datasource.api.response.GetMovieResponse
import com.atorrico.assignment.data.datasource.api.service.MovieRetrofitService
import com.atorrico.assignment.data.datasource.util.ApiResult
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieRetrofitService: MovieRetrofitService
) {

    suspend fun getMovieList(): ApiResult<GetMovieResponse> = movieRetrofitService.getMovieList()

    suspend fun getMovie(id: Int): ApiResult<MovieApiModel> = movieRetrofitService.getMovie(id)

}