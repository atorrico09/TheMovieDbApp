package com.atorrico.assignment.domain.usecase

import com.atorrico.assignment.data.datasource.api.model.MovieApiModel
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.domain.repository.MovieRepository
import com.atorrico.assignment.presentation.util.Result
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int): Result<MovieApiModel> = movieRepository.getMovie(id)

    suspend fun insertMovieDao(movie: MovieEntityModel) = movieRepository.insertMovie(movie)

    fun getMovieDao(id: Int) = movieRepository.getMovieDao(id)
}