package com.atorrico.assignment.data.remote

import com.atorrico.assignment.data.entities.Movie
import com.atorrico.assignment.data.entities.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("list/1?api_key=208ca80d1e219453796a7f9792d16776&language=en-US")
    suspend fun getAllMovies() : Response<MovieList>

    @GET("movie/{id}?api_key=208ca80d1e219453796a7f9792d16776")
    suspend fun getMovie(@Path("id") id: Int): Response<Movie>
}