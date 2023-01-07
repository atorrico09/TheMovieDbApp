package com.atorrico.assignment.domain.repository

import androidx.lifecycle.LiveData
import com.atorrico.assignment.data.datasource.api.model.MovieApiModel
import com.atorrico.assignment.data.datasource.api.response.GetMovieResponse
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.presentation.utils.Result

interface MovieRepository {

    suspend fun getMovie(id: Int): Result<MovieApiModel>

    suspend fun getMovieList(): Result<GetMovieResponse>

    fun getMovieDao(id: Int): LiveData<MovieEntityModel>

    fun getMovieListDao(): LiveData<List<MovieEntityModel>>

    suspend fun insertMovie(movie: MovieEntityModel)
}