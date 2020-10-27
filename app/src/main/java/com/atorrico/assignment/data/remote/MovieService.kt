package com.atorrico.assignment.data.remote

import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.data.entities.MovieList
import com.atorrico.assignment.utils.Constants.API_KEY_MOVIE_DB_APP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("list/1?api_key=$API_KEY_MOVIE_DB_APP&language=en-US")
    suspend fun getAllMovies() : Response<MovieList>

    @GET("movie/{id}?api_key=$API_KEY_MOVIE_DB_APP")
    suspend fun getMovie(@Path("id") id: Int): Response<Movie>
}