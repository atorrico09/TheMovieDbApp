package com.atorrico.assignment.data.datasource.api.service

import com.atorrico.assignment.data.datasource.api.response.GetGenreResponse
import com.atorrico.assignment.data.datasource.api.model.MovieApiModel
import com.atorrico.assignment.data.datasource.api.response.GetMovieResponse
import com.atorrico.assignment.presentation.utils.Constants.API_KEY_MOVIE_DB_APP
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieRetrofitService {
    @GET("list/1?api_key=$API_KEY_MOVIE_DB_APP&language=en-US")
    suspend fun getAllMovies(): GetMovieResponse

    @GET("movie/{id}?api_key=$API_KEY_MOVIE_DB_APP")
    suspend fun getMovie(@Path("id") id: Int): MovieApiModel

    @GET("genre/movie/list?api_key=$API_KEY_MOVIE_DB_APP")
    suspend fun getGenres(): GetGenreResponse
}